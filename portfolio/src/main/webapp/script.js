// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Service is the rent we pay for the privledge of living here on Earth', 'The peopl who say your dreams are impossible hav already quit on theirs', 'Th key to success is to focus on goals not obstacles', 'If you dont sacrifice fo what you want what you want becomes the sacrifice'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

    const boldGreeting = greeting.bold();
  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function getRandomQuoteUsingArrowFunctions() {
  fetch('/data').then(response => response.text()).then((quote) => {
    document.getElementById('myName-container').innerText = quote;
  });
}

function getMySayings() {
  fetch('/data').then(response => response.json()).then((mySayings) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content

    const myListElement = document.getElementById('myName-container');
    myListElement.innerHTML = '';
    myListElement.appendChild(
        createListElement('firstSaying: ' + mySayings.firstthing));
        console.log(mySayings.firstthing);
    myListElement.appendChild(
        createListElement('secondSaying: ' + mySayings.secondthing));
     console.log(mySayings.secondthing);
    myListElement.appendChild(
        createListElement('thirdSaying: ' + mySayings.thirdthing));
          console.log(mySayings.thirdthing); 
  });
}

function previousComments() {
   
fetch('/data').then(response => response.json()).then((Comments) => {
 console.log(Comments);
const myListElement = document.getElementById("commentsHistory");

myListElement.innerHTML = '';

  Comments.forEach((line) => {
        myListElement.appendChild(createListElement(line.substring(1,line.length - 1)));
         var linebreak = document.createElement("br");
        myListElement.appendChild(linebreak);
        console.log(line);
    });


});
}

function loginStatus() {
document.getElementById("ContactME").style.display="none";
          String itsTrue = "true";
   fetch('/Login').then(response => response.json()) .then((Status) => {
    

      if(Status.isLoggedIn.equals(thatsTrue) == true) {
           document.getElementById("ContactME").style.display="block";   
      }
   
   
   });

}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}






