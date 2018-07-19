import dis

def chap2():
    """
    long comment
    """
    # print("15分は" + str(15 / 60) + "時間です")
    print(3 + 0.5)

dis.dis(chap2)
print("-" * 60)
chap2()
