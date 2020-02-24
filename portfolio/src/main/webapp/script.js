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
function addRandomfact() {
  const computerFacts =
      ["90% of the world's data was created in the last 2 years!", "A average smartphone is over one million times faster than the Apollo Guidance Computer!",
       "CAPTCHA means-“Completely Automated Public Turing Test to tell Computers and Humans Apart”.", " The first 1GB hard disk drive was announced in 1980 which weighed about 550 pounds, and had a price tag of $40,000.", 
       "The original name of Windows was Interface Manager."];

  // Pick a random greeting.
  const randomFact = computerFacts[Math.floor(Math.random() * computerFacts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = randomFact;
}
