
# Mangaque

This application is designed for reading manga and supports both online and offline modes.

In online mode:

- The application allows users to view a manga feed with live updates through up and down scrolling.
- Users can access detailed information about each manga title, including the main info, description, number of chapters, and the content of the chapters (manga images).
- Users can add items to their favorites by clicking the LIKE icon or download the entire manga by clicking the SAVE icon.
The downloading process may take some time; it is recommended not to close the application or leave the download screen during this operation.
- The application also tracks user history, such as the last opened chapter, the last downloaded chapter, the date of downloading, and adding to favorites.
- Upon reopening the application, the feed will refresh, but only downloaded and saved manga items will be stored in the local database.

In offline mode:

- The feed displays only the downloaded or favorite manga.
- Users can read only the downloaded content.
 


## Screenshots

![App Screenshot](https://i.postimg.cc/4yVMbXr8/Screenshot-20240224-085447.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/dtcgQTyg/Screenshot-20240224-085539.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/y8vGTQbz/Screenshot-20240224-085651.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/MGLrCcwP/Screenshot-20240224-090015.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/g2Y58F4D/Screenshot-20240224-090601.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/Nf8SKf8F/Screenshot-20240224-091207.png/117x75?text=App+Screenshot+Here)
![App Screenshot](https://i.postimg.cc/tgJMv3Kn/Screenshot-20240224-091346.png/117x75?text=App+Screenshot+Here)

## Todo Features

- add UserSettings screen on third bottom bar icon clicked
- implement and use Theme for typography/dimansions/aso
- handle both screen orientations
- handle error states properly
- light/dark mode
- signup/login User
- search manga by name
- sort feed by filters
- downloading in background
- use crashlytics



## Installation

Application need API key to work. Please, add it to gradle.properties in format 

```bash
  MANGA_QUE_KEY="ApiKey here"
```
    