
function respond() {
    loadData();
    //initGear();
    //turnon();
}


function freeRespond() {
    freeLoadData();
    //initGear();
    //turnon();
}


function scrollToBottom() {
    window.scrollTo(0, document.body.scrollHeight);
}
function callServlet(callback) {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Set up the request
    var inputText = document.getElementById("prompt").value;

    // Encode the input text
    var encodedText = encodeURIComponent(inputText);

    xhr.open("GET", "/SandboxServlet?prompt=" + encodedText);

    // Set the response type to text
    xhr.responseType = "text";

    // Set up a callback function to handle the response
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Call the callback function with the response text
            callback(xhr.responseText);
        }
        else {
            // Log an error message to the console
            console.error("Error calling Servlet:", xhr.status);
        }
    };

    // Send the request
    xhr.send();
}

function callFreeServlet(callback) {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();
    // Set up the request
    var inputText = document.getElementById("prompt").value;
    // Encode the input text
    var encodedText = encodeURIComponent(inputText);
    xhr.open("GET", "/FreeboxServlet?prompt=" + encodedText);
    // Set the response type to text
    xhr.responseType = "text";
    // Set up a callback function to handle the response
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Call the callback function with the response text
            callback(xhr.responseText);
        }
        else {
            // Log an error message to the console
            console.error("Error calling Servlet:", xhr.status);
        }
    };
    // Send the request
    xhr.send();
}

function messageCount(callback) {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Set up the request
    var inputText = document.getElementById("email").value;

    // Encode the input text
    var encodedText = encodeURIComponent(inputText);

    xhr.open("GET", "/PollMessageServlet?email=" + encodedText);

    // Set the response type to text
    xhr.responseType = "text";

    // Set up a callback function to handle the response
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Call the callback function with the response text
            callback(xhr.responseText);

        }
        else {
            // Log an error message to the console
            console.error("Error calling Servlet:", xhr.status);
        }
    };

    // Send the request
    xhr.send();
}

function populate(callback) {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Set up the request
    var inputText = document.getElementById("email").value;

    // Encode the input text
    var encodedText = encodeURIComponent(inputText);

    xhr.open("GET", "/RetrieveMessageServlet?email=" + encodedText);

    // Set the response type to text
    xhr.responseType = "text";

    // Set up a callback function to handle the response
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Call the callback function with the response text
            callback(xhr.responseText);

        }
        else {
            // Log an error message to the console
            console.error("Error calling Servlet:", xhr.status);
        }
    };

    // Send the request
    xhr.send();
}
function loadData() {
    // Get the playground element
    var playground = document.getElementById("playground");

    // Call the Servlet and populate the playground element with the response
    callServlet(function(responseText) {
        // Create a text node with the response text

        playground.innerHTML = playground.innerHTML + " " + responseText + "";
        const div = document.getElementById("outputPanel");
        div.scrollTop = div.scrollHeight - div.clientHeight;
        //turnoff();
        setTimeout("scrollToBottom()",1000);
        document.getElementById("prompt").value = "";
        if( document.getElementById("promptType").value === 'image')
        {
            document.getElementById("prompt").value = "image:";
        } else {
            document.getElementById("prompt").value = "";
        }
    });
}

function freeLoadData() {
    // Get the playground element
    var playground = document.getElementById("playground");

    // Call the Servlet and populate the playground element with the response
    callFreeServlet(function(responseText) {
        // Create a text node with the response text
        playground.innerHTML = playground.innerHTML + " " + responseText + "";
        const div = document.getElementById("outputPanel");
        div.scrollTop = div.scrollHeight - div.clientHeight;
        setTimeout("scrollToBottom()",1000);
        if( document.getElementById("promptType").value === 'image')
        {
            document.getElementById("prompt").value = "image:";
        } else {
            document.getElementById("prompt").value = "";
        }
    });
}

function poll(){
    var email = document.getElementById("email");
    messageCount(function(responseText) {
        // Create a text node with the response text

        const obj = JSON.parse('{' + responseText.replace(/'/g,'"') + "}");
        let count = obj.message_count;

        while( count-- >= 0 ){
            var playground = document.getElementById("playground");
            populate(function(responseText) {
                playground.innerHTML = playground.innerHTML + " " + responseText + "";
                const div = document.getElementById("outputPanel");
                div.scrollTop = div.scrollHeight - div.clientHeight;
                //turnoff();
                setTimeout("scrollToBottom()",1000);
                document.getElementById("prompt").value = "";
            });
        }
    });
}
function typeOutWithHTML(text) {
    let index = 0;
    element = document.getElementById("playground");
    const type = setInterval(() => {
        const currentChar = text[index];
        if (currentChar === "<") {
            const endIndex = text.indexOf(">", index) + 1;
            element.innerHTML = element.innerHTML + insertAdjacentHTML("beforeend", text.substring(index, endIndex));
            index = endIndex;
        } else {
            element.insertAdjacentText("beforeend", currentChar);
            element.innerHTML = element.innerHTML + insertAdjacentHTML("beforeend", text.substring(index, endIndex));
            index = endIndex;
            index++;
        }
        if (index === text.length) clearInterval(type);
    }, 50);
}

function generateRandomString() {
    const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const numbers = "0123456789";
    let result = "";
    // Generate first character
    result += letters.charAt(Math.floor(Math.random() * letters.length));
    // Generate remaining characters
    for (let i = 1; i < 10; i++) {
        const characters = letters + numbers;
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}



function initGear(){
    document.getElementById("playground").innerHTML = document.getElementById("playground").innerHTML + "<div class='gear'></div>";
    var gear = document.getElementById("gear"); // get the gear element by its ID
    gear.style.width = "30%"; // set the width of the gear
    gear.style.height = "30%"; // set the height of the gear
    gear.style.position = "fixed"; // set the position to fixed so it stays in place
    gear.style.top = "50%"; // set the vertical position to the middle of the screen
    gear.style.left = "50%"; // set the horizontal position to the middle of the screen
    gear.style.transform = "translate(-50%, -50%)"; // move the gear to the center of the screen
    gear.style.backgroundImage = "url('https://genpen.ai/resources/demo/images/genpenai-logo-rich.png')"; // set the image for the gear
    gear.style.backgroundSize = "cover"; // make sure the image covers the entire div
    gear.style.zIndex = "999"; // set the z-index so the gear is on top of everything else

}
var angle = 0; // initial angle of the gear

function animate() {
    angle += 5; // increase the angle by 5 degrees for each frame
    var gear = document.getElementById("gear");
    gear.style.transform = "translate(-50%, -50%) rotate(" + angle + "deg)"; // rotate the gear
    requestAnimationFrame(animate); // request the next animation frame
}

/*
function turnon() {
    var gear = document.getElementById("gear");
    gear.style.display = "inline-block"; // show the gear
    animate(); // start the animation
}

function turnoff() {
    var gear = document.getElementById("gear");
    gear.style.display = "none"; // hide the gear
}
*/
function handleKeyDown(event) {
    if (event.key === "Enter") {
        // if the Enter key is pressed, submit the form
        event.preventDefault(); // prevent the default Enter key behavior (submitting the form)
        respond();//document.getElementById("prompt").submit(); // submit the form with the ID "myForm"
    }
}

//const myDiv = document.querySelector('#playground');

//const observer = new MutationObserver(function(mutations) {
//    mutations.forEach(function(mutation) {
//        if (mutation.type === 'childList') {
//            // the content of the div has changed, fire your function here
//            console.log('Content changed!');
//            turnoff();
//        }
//    });
//});

//observer.observe(myDiv, {childList: true});

const popups = document.querySelectorAll('.popup-text');
popups.forEach(popup => {
    const text = popup.textContent.trim();
    popup.setAttribute('data-text', text);
});
