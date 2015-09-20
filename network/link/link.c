#include <stdio.h> // fprintf
#include <string.h> // memset
#include <unistd.h> // close
#include <sys/ioctl.h> // ioctl
#include <arpa/inet.h> // htons
#include <sys/socket.h> // if.h
#include <linux/if.h> // struct ifreq
#include <net/ethernet.h> // struct ether_header
#include <netpacket/packet.h> // struct sockaddr_ll

// return int: socket fd
//
// device:       NIC name
// promisc_flag: promise cast mode (also receive packets for others)
// ip_only:      if 1 then IP only, if 0 then all packets
int init_raw_socket(char *device, int promisc_flag, int ip_only)
{
    struct ifreq ifreq;
    struct sockaddr_ll sa;
    int    soc;

    // acquire fd for data linking, requiring sudo
    if (ip_only) {
        if ((soc = socket(PF_PACKET, SOCK_RAW, htons(ETH_P_IP))) < 0) {
            perror("socket");
            return -1;
        }
    } else {
        if ((soc = socket(PF_PACKET, SOCK_RAW, htons(ETH_P_ALL))) < 0) {
            perror("socket");
            return -1;
        }
    }

    // prepare ifreq
    memset(&ifreq, 0, sizeof(struct ifreq));
    strncpy(ifreq.ifr_name, device, sizeof(ifreq.ifr_name) - 1);

    // get index of network interface for given name
    if (ioctl(soc, SIOCGIFINDEX, &ifreq) < 0) {
        perror("ioctl");
        close(soc);
        return -1;
    }

    // prepare struct sockaddr_ll
    sa.sll_family = PF_PACKET;
    if (ip_only) {
        sa.sll_protocol = htons(ETH_P_IP);
    } else {
        sa.sll_protocol = htons(ETH_P_ALL);
    }
    sa.sll_ifindex = ifreq.ifr_ifindex;

    // bind socket
    if (bind(soc, (struct sockaddr *)&sa, sizeof(sa)) < 0) {
        perror("buid");
        close(soc);
        return -1;
    }

    if (promisc_flag) {
        // get device flag
        if (ioctl(soc, SIOCGIFFLAGS, &ifreq) < 0) {
            perror("ioctl");
            close(soc);
            return -1;
        }

        // enable IFF_PROMISC
        ifreq.ifr_flags = ifreq.ifr_flags|IFF_PROMISC;

        // write SIOCSIFFLAGS
        if (ioctl(soc, SIOCSIFFLAGS, &ifreq) < 0) {
            perror("ioctl");
            close(soc);
            return -1;
        }
    }

    return soc;
}

// Convert MAC address to string
char *my_ether_ntoa_r(u_char *hwaddr, char *buf, socklen_t size)
{
    snprintf(buf, size, "%02x:%02x:%02x:%02x:%02x:%02x",
            hwaddr[0], hwaddr[1], hwaddr[2], hwaddr[3], hwaddr[4], hwaddr[5]);
    return buf;
}

int print_ether_header(struct ether_header *eh, FILE *fp)
{
    char buf[80];

    fprintf(fp, "[ether_header]---------------\n");
    fprintf(fp, "ether_dhost=%s\n", my_ether_ntoa_r(eh->ether_dhost, buf, sizeof(buf)));
    fprintf(fp, "ether_shost=%s\n", my_ether_ntoa_r(eh->ether_shost, buf, sizeof(buf)));
    fprintf(fp, "ether_type=%02X",  ntohs(eh->ether_type));

    switch (ntohs(eh->ether_type)) {
        case ETH_P_IP:
            fprintf(fp, "(IP)\n");
            break;
        case ETH_P_IPV6:
            fprintf(fp, "(IPv6)\n");
            break;
        case ETH_P_ARP:
            fprintf(fp, "(ARP)\n");
            break;
        default:
            fprintf(fp, "(unknown)\n");
            break;
    }
    return 0;
}

int main(int argc, char *argv[], char *envp[])
{
    int    soc, size;
    u_char buf[2048];

    if (argc <= 1) {
        fprintf(stderr, "link device-name\n");
        return 1;
    }

    // get fd for data linking which dumps all packets
    if ((soc = init_raw_socket(argv[1], 1, 0)) == -1) {
        fprintf(stderr, "init_raw_socket:error:%s\n", argv[1]);
        return -1;
    }

    // infinite loop
    printf("Start to read packets...\n");
    while (1) {
        // Use `read` to read from data link layer
        if ((size = read(soc, buf, sizeof(buf))) <= 0) {
            perror("read");
            continue;
        }

        // Packet must be larger than ether header
        if (size < sizeof(struct ether_header)) {
            fprintf(stderr, "read size(%d) < %d (size of struct ether_header)\n", size, (int)sizeof(struct ether_header));
        }

        print_ether_header((struct ether_header *)buf, stdout);
    }

    close(soc);
    return 0;
}
