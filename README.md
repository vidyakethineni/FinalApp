# Midterm App

This application is a comprehensive Food Delivery App that allows users to order a variety of food items from different restaurants. The app incorporates Firebase services for authentication, Firestore for databases, and Cloud Storage for image storage.


## Functionality 

The following **required** functionality is completed:
Sign-up Screen
Users can sign up by entering their email and name.
Image upload functionality is available, allowing users to upload a profile picture from the gallery or camera.

Navigation Drawer and Home Screen
The Navigation Drawer displays user information, including a profile picture, name, and email.
Recent restaurants are horizontally scrollable, and all restaurants are vertically scrollable.
Each restaurant has a location within 50 miles of the user.
Restaurant images are stored in Firebase Cloud Storage.

Restaurant Screen
Displays restaurant/food images with swipe functionality.
Lists all food items with prices.
Users can add or delete items from their order, and each item keeps track of quantity.
Checkout button navigates to the Checkout Screen.

Checkout Screen
Lists added food items with details.
Users can adjust quantity, enter delivery address, and provide special instructions.
Modify Order button allows users to go back to the Restaurant Screen to make changes.
Place Order button saves order details to the database, calculates delivery time, and sets up a background service for notifications.

Orders Screen
Displays details of past orders, including food items, prices, date and time, quantity, restaurant, and delivery address.
Track Order button navigates to the Map Screen to display the order location.

Recent Orders Screen
Lists all past orders sorted by date.
"Place this order again" button allows users to recreate a past order.

Calendar View Screen
Displays a calendar view with highlighted days based on spending information stored in the database.

## Extensions

Potential extensions include:
- Allow users to rate and review restaurants and food items
- Implementation to support multiple languages

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='SignUp, LogIn, Home Screen Video Walkthrough.gif' title='SignUp, LogIn, Home Screen Video Walkthrough' width='50%' alt='SignUp, LogIn, Home Screen Video Walkthrough' />
<img src='Order Video Walkthrough.gif' title='Order Video Walkthrough' width='50%' alt='Order Video Walkthrough' />
<img src='Other Video Walkthrough.gif' title='Other Video Walkthrough' width='50%' alt='Other Video Walkthrough' />


GIF created with [EzGif](https://ezgif.com/) 

## Notes

Challenges encountered during the coding process included:
  - Integration of Firebase services (authentication, Firestore, and Cloud Storage)
  - Designing and implementing complex UI layouts
  - View model implementation

## License

    Copyright [2023] [Vidya Kethineni]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
