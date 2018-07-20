import tkinter

# ウィンドウ作成
root = tkinter.Tk()
root.title("リリーにしつもん")
root.minsize(640, 480)
root.option_add("*font", ["", 11])

# 画像表示
canvas = tkinter.Canvas(bg="black", width=640, height=480)
canvas.place(x=0, y=0)
img = tkinter.PhotoImage(file="img3/chap3-back.png")
canvas.create_image(320, 240, image=img)

# テキスト表示
question = tkinter.Label(text="知りたいのは何分かな?", bg="white")
question.place(x=100, y=40)

# テキストボックス表示
entry = tkinter.Entry(width=10, bd=4)
entry.place(x=44, y=130)

def ask_click():
    print("ボタンがクリックされました!")

# 質問ボタン表示
askbutton = tkinter.Button(text="聞く")
askbutton.place(x=258, y=128)
askbutton["command"] = ask_click

# 答え表示
answer = tkinter.Label(text="……………", bg="white")
answer.place(x=118, y=235)

# メインループ
root.mainloop()
