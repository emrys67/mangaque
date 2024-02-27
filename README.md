
# Mangaque

Application for reading manga. It can work in online and offline mode.


 In online mode application allow user to see the manga feed with live on updown scrolling updates. User can proceed to each fetched manga title and check main info / description / number of chapters and actually chapters content (manga images).

 User is allowed to add item to favorites, by clicking LIKE or to download full manga item by clicking on SAVE icon. Downloading process can take a time, it's recomended to not close an application during this operation and to not leave the screen with downloading process. Also user can there is some history tracking: last chapter opened/last downloaded chapter/date of downloading/adding to the favorite a.s.o.
 After application reopening all feed is refreshing, only downloaded and saved manga items are keeping store in local database.

 In offline mode in feed is only displaying downlaoded/added to favorite manga. Can be read only downloaded content.
 


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
    