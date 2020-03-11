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
function loadComments(){
    fetch('/data').then(response => response.json()).then((UserComment) => {
    const commentListElement = document.getElementById('comment-list');   
    UserComment.forEach((UserComment) => {
      commentListElement.appendChild(createCommentElement(UserComment));
    })    
  });
    var commentSubmissionElement = document.getElementById('comment-section');
    fetch('/home').then(response => response.json()).then((UserSession) => {
    if(UserSession.isloggedIn != true){
        commentSubmissionElement.appendChild(createLoginElement(UserSession));            
    }
    else{
        const greetingElement = document.getElementById('greeting');
        greetingElement.appendChild(createGreetingElement(UserSession));        
        commentSubmissionElement.appendChild(createLogoutElement(UserSession));
        commentSubmissionElement.style.display = "block";
    }    
  });
  
}
function createCommentElement(comment){
    const commentElement = document.createElement('li');
    commentElement.className = 'comment';    
    var date = new Date(comment.timestamp * 1000);   
    var hours = date.getHours();    
    var minutes = "0" + date.getMinutes();    
    var seconds = "0" + date.getSeconds();    
    var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    var userEmail = comment.emailaddress == null ? "Anon" : comment.emailaddress;
    commentElement.innerHtml = "<span> <strong>"+ userEmail +"@"+ formattedTime+"</strong> "+ comment.body+ "</span>";
    return commentElement;
}
function createLoginElement(session){
    const logintextElement = document.createElement('div');
    const logintitleElement = document.createElement('h4');
    logintitleElement.innerText = 'Login to leave a comment';
    logintextElement.appendChild(logintitleElement);
    const loginbutton = document.createElement('button');
    loginbutton.innerText = "Login";
    loginbutton.href = session.loginUrl;
    logintextElement.appendChild(loginbutton);
    return logintextElement;
}
function createGreetingElement(session){
    const greeting = document.createElement('p');
    greeting.innerText = "Hello " + session.userEmail + "!";
    return greeting;
}
function createLogoutElement(session){
    const logoutbutton = document.createElement('button');
    logoutbutton.innerText = "Logout";
    logoutbutton.href = session.logoutUrl;
    return logoutbutton;
}