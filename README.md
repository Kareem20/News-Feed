# News Feed


**About Project**

it's the final project of the Youth Enablement for Freelancing initiative 
from the Ministry of Communications and Information Technology. 

it's a news application that gets the articles from the API of the guardian.


[this link is the documentation of API](https://open-platform.theguardian.com/documentation/)

There is a section for the covid-19 report to get the number of deaths, recovered and confirmed people in the worldwide and 
of the specific country.
Users can choose the country that they want to know the covid-19 report.


[API for the worldwide report](https://disease.sh/v3/covid-19/all)


[API for a specific country (ex: Egypt).](https://disease.sh/v3/covid-19/countries/Egypt)

**Main features**
- Get articles from an international newspaper (The Guardian).
- Organize the articles in a good way to make the user easy to read.
- Search for any topics and read articles about them.
- Able to determined how many articles do you need on the home page and sections.
- Able to determine the date of the article and how to sort the article will be.
- Section Covid-19 to know The worldwide report and specific country report.

**Some Screen Shots from app**

<img src = "https://user-images.githubusercontent.com/32216396/100517408-ff1cdd00-3192-11eb-8a69-792eb7259343.png" width ="200" /> <img src = "https://user-images.githubusercontent.com/32216396/100517420-11971680-3193-11eb-8457-eebe0f127115.png" width ="200" /> <img src = "https://user-images.githubusercontent.com/32216396/100517434-25db1380-3193-11eb-998b-d5db773358f2.png" width ="200" />

**icons from website (icon8).**


**Libraries that i use**

code from gradle file.

 
  
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.appcompat:appcompat:1.2.0'
    
    //sdp library.
    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    
    // Retrofit & OkHttp dependency
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    
    // JSON Parsing
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    
    //cardView
    implementation 'androidx.cardview:cardview:1.0.0'
    
    //picasso
    implementation 'com.squareup.picasso:picasso:2.71828'
    
    //loader
    implementation "androidx.loader:loader:1.1.0"
    
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'
    implementation "com.google.android.material:material:1.0.0"
    
    
    
if you find problems on trying it, please email me (kareemabdelrhmane@gmail.com).


