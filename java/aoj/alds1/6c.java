import java.util.*;

class Main {
    class Card {
        char ch;
        int num;

        public Card(char ch, int num) {
            this.ch = ch;
            this.num = num;
        }
    }

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Card[] cards = new Card[n];
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ", 2);
            cards[i] = new Card(
                    line[0].charAt(0),
                    Integer.parseInt(line[1]));
        }
        Card[] sorted = cards.clone();

        this.bubbleSort(sorted);
        this.quickSort(cards, 0, n-1);

        boolean stable = true;
        for (int i = 0; i < n; i++) {
            if (cards[i].ch != sorted[i].ch) {
                stable = false;
                break;
            }
        }
        if (stable) {
            System.out.println("Stable");
        } else {
            System.out.println("Not stable");
        }

        for (int i = 0; i < n; i++) {
            Card card = cards[i];
            System.out.printf("%c %d\n", card.ch, card.num);
        }
    }

    private void quickSort(Card[] cards, int left, int right) {
        if (left >= right) return;

        int middle = this.partition(cards, left, right);
        this.quickSort(cards, left, middle - 1);
        this.quickSort(cards, middle + 1, right);
    }

    private int partition(Card[] cards, int p, int r) {
        Card x = cards[r];
        int i = p - 1;

        for (int j = p; j < r; j++) {
            if (cards[j].num <= x.num) {
                i++;
                Card tmp = cards[i];
                cards[i] = cards[j];
                cards[j] = tmp;
            }
        }

        Card tmp = cards[i+1];
        cards[i+1] = cards[r];
        cards[r] = tmp;
        return i + 1;
    }

    private void bubbleSort(Card[] sorted) {
        boolean found = true;

        int i = 0; // count of sorted cards
        while (found) {
            found = false;
            for (int j = sorted.length - 1; j > i; j--) {
                if (sorted[j - 1].num > sorted[j].num) {
                    Card temp = sorted[j - 1];
                    sorted[j - 1] = sorted[j];
                    sorted[j] = temp;

                    found = true;
                }
            }
            i++;
        }
    }
}
