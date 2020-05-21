# School of Computing &mdash; Year 4 Project Proposal Form

> Edit (then commit and push) this document to complete your proposal form.
> Make use of figures / diagrams where appropriate.
>
> Do not rename this file.

## SECTION A

|                     |                   |
|---------------------|-------------------|
|Project Title:       | Gym vision            |
|Student 1 Name:      | Ayman El Gendy    |
|Student 1 ID:        | 15395461          |
|Student 2 Name:      | Fawaz Alsafadi    |
|Student 2 ID:        | 15380871           |
|Project Supervisor:  | Alistair Sutherland           |

> Ensure that the Supervisor formally agrees to supervise your project; this is only recognised once the
> Supervisor assigns herself/himself via the project Dashboard.
>
> Project proposals without an assigned
> Supervisor will not be accepted for presentation to the Approval Panel.

## SECTION B

> Guidance: This document is expected to be approximately 3 pages in length, but it can exceed this page limit.
> It is also permissible to carry forward content from this proposal to your later documents (e.g. functional
> specification) as appropriate.
>
> Your proposal must include *at least* the following sections.


### Introduction
 Incorrect form in the gym doing exercises is very common. Our aim with this project is to provide a tool for active individuals to correct their posture any form during physical activity to avoid injury and get the most benefit from their exercises.

### Outline
The project will involve developing software that will, through a camera, allow a person to correct their form while performing different exercises. 
The user will perform the exercise in front of their camera, which will be recorded and analysed by the software to give feedback in real time. 
This feedback will guide the user to improving their form, the users from will be compared to correct form for the particular sport and will advise the user on how to correct their form.
Through the use of existing computer vision libraries and our own custom libraries we will convert the users real time image to 3D model and map their body and the various angles that are required for our software to calculate their form. 


### Background
 Starting off at the gym can be a daunting task for some people, we noticed that the main reason for this is that people are not sure how to perform certain exercises properly,
and may feel embarrassed doing the wrong exercise or just don't want to risk injury doing it wrong. We saw an opportunity to address this issue through the use of computer
vision technology. As people who would still be considered beginners at the gym, there are many exercises that we still struggle to perform correctly, especially 
compound movements such as (Deadlifts and Squats). This makes it hard for us to progress as it can easily cause injury. So we used our own experience for a lack of resources to perfect
our form as the driving motivation for this project.

### Achievements

The app will be aimed at people who want to become more comfortable completing their ideal gym routine, without the risk of injury/feeling embarrassed.

### Justification

> Why/when/where/how will it be useful?

The software will be useful because people will always be new to the gym and there is no convenient way currently for people to perfect their form on their own. People who don't
want to pay for personal trainers must attempt to fix their form through less than ideal methods such as recording themselves and posting it on a forum for advice. This software will 
eliminate long time these people must wait for feedback.

The program will be used whenever the user intends on learning a new exercise for their routine. They should attempt the exercise in front of the camera with low weight and adjust 
their movements based on the feedback from our software.

The software will be used either at home using personal weights/doing the movements without weights, or in the gym if they are able to bring access the software and hardware 
there. 


### Testing

Testing will be carried out during the entire application development lifecycle, these are some of the testing that will be carried out.

##### Unit testing
We will maintain a bank of Unit tests that insure full testing of the application code

##### Functional testing 
We will continuously test different sections of the application and all the functionality as it is added and at the end 

##### User testing
We will hold user testing during the applications beta and final release where we will allow users to test our app and perform gym exercises to insure that it benefits them and works as intended


### Development

In order to improve and accelerate our workflow we will be using Microsoft Azure DevOps to allow us to work in an Agile manner. 
We will use the boards and sprint manager to create tasks and mark competed tasks so that we have greater transparency of our work to be done and the work completed.
We will meet frequently to discuss the work completed and create a backlog of tasks to go through.

### Programming language(s)
1.  Python
2.  C++
3.  JSON
4.  YAML

### Programming tools / Tech stack
* OpenGL
* Microsoft Azure
* SQL Database
* OpenCV
* Numpy

### Hardware
1.  Intel Neural Compute Stick 2 (NCS2) Deep Neural Network USB or Kinect
2.  Windows Laptop

### Learning Challenges
1.  C++ language
2.  Computer vision
3.  Microsoft Azure
4.  OpenGL
5.  OpenCV
6.  Numpy


### Breakdown of work

#### Student 1

* My first task is going to be to research openCV and work through the tutorials to get a basic introduction to computer vision libraries
and algorithms. 

* My next step will be to research the following computer vision subfields: Human detection, Motion tracking, and Pose estimation.

* We plan on using Python with openCV as we are more familiar with that language, however I will try to get a basic understanding of C++ as Python
is just a wrapper for the original C/C++ openCV code. 

* I will also be working alongside my partner to maintain the Microsoft Azure environment and deploying our app to the cloud.

* Once the research phase is done, I will start to focus my attention on developing the software. My plan is to start off with a basic human
detection algorithm, then implement motion detection, and finally I will work with my partner to figure out the pose estimation algorithms necessary for analysis.




#### Student 2

* I will begin by working through some tutorials on openGL in order to gain the skills necessary to implement a custom openGL framework that will implement a skeletal animation to aid our pose estimation later. 

* When I have the desired model developed, I will begin working on Human pose estimation algorithms that will help differentiate between good and bad forms. I plan to implement this algorithms for the motion tracking implementation created by my partner while I was developing the skeletal model.

* I will be mounting our application to docker/Kubernetes containers by creating the file, image and containers.

* Alongside the application development I will be creating and maintaining a Microsoft Azrure environment. To this environment I will deploy our Dockerised app and set up services so that it can handle network traffic and data. I will also set up and configure a host of Cloud services such as database, service, function apps and NGINX.

* I will set up a CI/CCD pipeline to automate the application from development to testing to release. This will help insure constant testing of our application at every step and allow us to continually add tests as we develop new functionality.

### Example

<!-- Basically, just use HTML! -->

<p align="center">
  <img src="./res/cat.png" width="300px">
</p>

