import sys
from PyQt5 import QtWidgets
from PyQt5.QtGui import QImage, QPalette, QBrush
from PyQt5.QtWidgets import (QApplication, QMainWindow,
                             QLabel, QPushButton, QWidget,
                             QStackedLayout, QListWidget,
                             QVBoxLayout, QStackedWidget,
                             QGridLayout, QDialog)
from PyQt5.QtCore import QRect, Qt,QSize
from PyQt5.QtGui     import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore    import *
from Squat import ex
from Auth import account as auth

class Ui(object):
    def setupUi(self,homeScreen):

        Widget = QWidget()
        Widget.resize(800, 600)
        bckImage = QImage("Background.jpg")
        background = bckImage.scaled(QSize(800,600))
        p = Widget.palette()
        p.setBrush(10,QBrush(background))
        Widget.setPalette(p)
        self.mylogin = auth()

        self.loginBtn = QtWidgets.QPushButton("Login",Widget)
        self.loginBtn.setGeometry(325, 350, 150, 40)
        self.loginBtn.setStyleSheet("background-color: #E75480;"
        "color: white;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )


        self.registerBtn = QPushButton("Register",Widget)
        self.registerBtn.setGeometry(325, 400, 150, 40)
        self.registerBtn.setStyleSheet("background-color: #E75480;"
        "color: white;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;")


        self.username = QLineEdit("Username", Widget)
        self.username.setGeometry(325, 250, 150, 40)
        self.username.setText("")
        self.username.setPlaceholderText("Username")

        self.password = QLineEdit("Password", Widget)
        self.password.setGeometry(325, 300, 150, 40)
        self.password.setText("")
        self.password.setPlaceholderText("Password")

        self.loginBtn.clicked.connect(lambda: self.mylogin.login(self.username.text(),self.password.text(),homeScreen))
        self.registerBtn.clicked.connect(lambda: self.registerPage(homeScreen))
        return Widget
    def registerPage(self,homeScreen):
        homeScreen.setCurrentIndex(2)
