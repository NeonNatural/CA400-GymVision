import sys
from PyQt5.QtWidgets import (QApplication, QMainWindow,
                             QLabel, QPushButton, QWidget,
                             QStackedLayout, QListWidget,
                             QVBoxLayout, QStackedWidget,
                             QGridLayout, QDialog)
from PyQt5.QtCore import QRect, Qt
from Squat import ex
from ExerciseScreen import Ui as ExUi
from Login import Ui as loginUi
from Register import Ui as registerUi

class Ui(object):
    def setupUi(self, MainWindow):

        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(800, 600)
        self.homeScreen = QStackedLayout()
        self.loginUi = loginUi()
        self.registerUi = registerUi()
        self.loginPage = self.loginUi.setupUi(self.homeScreen)
        self.exercises2 = ExUi.setupUi(self)
        self.registerPage = self.registerUi.setupUi(self.homeScreen)




        self.homeScreen.addWidget(self.loginPage)
        self.homeScreen.addWidget(self.exercises2)
        self.homeScreen.addWidget(self.registerPage)



    #def loginPageUi(self):
        #self.loginBtn = QPushButton("Login",self.loginPage)
        #self.loginBtn.setGeometry(140,180,200,30)


class Main(QMainWindow, Ui):

    def __init__(self):
        super(Main, self).__init__()
        self.setupUi(self)

        #self.loginBtn.clicked.connect(self.homeScreenWin)

    #def homeScreenWin(self):
        #self.homeScreen.setCurrentIndex(1)



if __name__ == "__main__":
    app = QApplication(sys.argv)
    M = Main()
    sys.exit(app.exec())
