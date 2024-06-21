# CSI 460 Final - API Application by Michael Splaver

NOTE: Currently Tracking **ONE** major bug in the issues tab, click on Issues for more details

This app can interact with my self-hosted API by using GET, POST, PATCH, DELETE HTTP requests. For this project I decided to utilize the same API that we setup earlier in the quarter (Students API).

### Landing Page
Here you can navigate between the different pages of the app depending on how you want to interact with the API
![1](https://github.com/MichaelSplaver/ApiProjectCSI460Final/assets/104462467/62bb963b-d464-4313-98b3-6e466c58c675)

### List View Page
Here you can view all the currently available Students through the API in a recycler view (GET Request)
![2](https://github.com/MichaelSplaver/ApiProjectCSI460Final/assets/104462467/7dc1f7bd-8e4b-42b5-85a6-ebd0bc720150)

### Create New Student Page
Here you can input all your data for creating a new student, the inputs will be validated before (POST Request)ing to the API
![3](https://github.com/MichaelSplaver/ApiProjectCSI460Final/assets/104462467/780e6255-89d2-454b-b152-3154b5669a9e)

#### Edite/Delete Students Page
Here you can view all the currently available Students through the API in a recycler view. From this view you can also select to modify (leading to the below Modify Student Page) or delete the selected Student. (DELETE Request)
![4](https://github.com/MichaelSplaver/ApiProjectCSI460Final/assets/104462467/72606072-9d85-49a4-887f-e0c14b5616c4)

#### Modify Student Page
Here you modify and save change to an existing Student in the API. All data values will be automatically populated and you cannot change the ID field. (PATCH Request)
![5](https://github.com/MichaelSplaver/ApiProjectCSI460Final/assets/104462467/6314a066-c852-45bf-a122-1fd52851c73e)
