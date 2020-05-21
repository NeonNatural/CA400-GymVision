import sys
from PyQt5 import QtWidgets
from PyQt5.QtGui import QPainter, QColor, QPen,QImage, QPalette, QBrush,QPixmap,QIcon

from PyQt5.QtWidgets import (QApplication, QMainWindow,
                             QLabel, QPushButton, QWidget,
                             QStackedLayout, QListWidget,
                             QVBoxLayout, QStackedWidget,
                             QGridLayout, QDialog)

from PyQt5.QtCore import QRect, Qt, QSize
from Squat import ex as Squat
from ShoulderPress import ex as ShoulderPress
from Deadlift import ex as Deadlift
from Bench import ex as Bench
from Lunge import ex as Lunge
from Row import ex as Row
from Skullcrusher import ex as SkullCrusher


class Ui(object):
    def setupUi(self):
        Widget = QWidget()
        Widget.resize(800, 650)
        bckImage = QImage("Background.jpg")
        background = bckImage.scaled(QSize(800,670))
        p = Widget.palette()
        p.setBrush(10,QBrush(background))
        Widget.setPalette(p)

        self.squatBtn = QPushButton("Squat",Widget)
        self.squatBtn.setGeometry(20, 20, 760, 80)
        self.squatBtn.setIcon(QIcon('Squat.png'))
        self.squatBtn.setIconSize(QSize(70,50))
        self.squatBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.shldrPress = QPushButton("Shoulder Press",Widget)
        self.shldrPress.setGeometry(20, 110, 760, 80)
        self.shldrPress.setIcon(QIcon('ShoulderPress.jpg'))
        self.shldrPress.setIconSize(QSize(70,50))
        self.shldrPress.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.deadliftBtn = QPushButton("Deadlift",Widget)
        self.deadliftBtn.setGeometry(20, 200, 760, 80)
        self.deadliftBtn.setIcon(QIcon('Deadlift.png'))
        self.deadliftBtn.setIconSize(QSize(70,50))
        self.deadliftBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.benchBtn = QPushButton("Bench",Widget)
        self.benchBtn.setGeometry(20, 290, 760, 80)
        self.benchBtn.setIcon(QIcon('Bench.png'))
        self.benchBtn.setIconSize(QSize(70,50))
        self.benchBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.lungeBtn = QPushButton("Lunge",Widget)
        self.lungeBtn.setGeometry(20, 380, 760, 80)
        self.lungeBtn.setIcon(QIcon('lunge.png'))
        self.lungeBtn.setIconSize(QSize(70,50))
        self.lungeBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.rowBtn = QPushButton("Row",Widget)
        self.rowBtn.setGeometry(20, 470, 760, 80)
        self.rowBtn.setIcon(QIcon('row.png'))
        self.rowBtn.setIconSize(QSize(70,50))
        self.rowBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )
        self.skullcrusherBtn = QPushButton("SkullCrusher",Widget)
        self.skullcrusherBtn.setGeometry(20, 560, 760, 80)
        self.skullcrusherBtn.setIcon(QIcon('skullcrush.png'))
        self.skullcrusherBtn.setIconSize(QSize(70,50))
        self.skullcrusherBtn.setStyleSheet("background-color: #FFFFFF;"
        "color: black;"
        "font-family:century gothic;"
        "font-size:20px;"
        "border-radius:20px;"
        )

        self.Squat = Squat()
        self.ShoulderPress = ShoulderPress()
        self.Deadlift = Deadlift()
        self.Bench = Bench()
        self.Lunge = Lunge()
        self.Row = Row()
        self.SkullCrusher = SkullCrusher()




        self.squatBtn.clicked.connect(lambda: self.Squat.analyze())
        self.shldrPress.clicked.connect(lambda: self.ShoulderPress.analyze())
        self.deadliftBtn.clicked.connect(lambda: self.Deadlift.analyze())
        self.benchBtn.clicked.connect(lambda: self.Bench.analyze())
        self.lungeBtn.clicked.connect(lambda: self.Lunge.analyze())
        self.rowBtn.clicked.connect(lambda: self.Row.analyze())
        self.skullcrusherBtn.clicked.connect(lambda: self.SkullCrusher.analyze())



        return Widget
