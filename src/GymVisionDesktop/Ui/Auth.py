import pyrebase
from getpass import getpass

class account():
    def __init__(self):
        self.firebaseConfig = {
            "apiKey": "AIzaSyCHRCd569bCvvULZ_6o28ITuonbAE-6uw4",
            "authDomain": "gymvision-264f8.firebaseapp.com",
            "databaseURL": "https://gymvision-264f8.firebaseio.com",
            "projectId": "gymvision-264f8",
            "storageBucket": "gymvision-264f8.appspot.com",
            "messagingSenderId": "750952290074",
            "appId": "1:750952290074:web:40c95abe0ac7909b273ea3",
            "measurementId": "G-BRQ9GGC97J"
        }


        self.firebase = pyrebase.initialize_app(self.firebaseConfig)
        self.auth = self.firebase.auth()






    def createAcc(self,username,password,homeScreen):

        try:

            if username == "" or password == "":
                print("Please enter valid details")
            else:
                self.auth.create_user_with_email_and_password (username,password)
                homeScreen.setCurrentIndex(0)
        except:
            pass

    def login(self,username,password,homeScreen):
        try:
            self.auth.sign_in_with_email_and_password(username,password)
            homeScreen.setCurrentIndex(1)
        except:
            print("Invalid details")
