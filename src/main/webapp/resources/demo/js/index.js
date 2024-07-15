// get  github login btn, google login btn, facebook login btn
let githubLoginBtn = document.getElementsByClassName('github-login-btn');
let googleLoginBtn = document.getElementsByClassName('google-login-btn');
let facebookLoginBtn = document.getElementsByClassName('facebook-login-btn');
let appleLoginBtn = document.getElementsByClassName('apple-login-btn');

let firebaseConfig = {
    apiKey: "AIzaSyAWTE9Md_1DPHo85HQbKC79PaKnIb24M1Q",
    authDomain: "genpen.firebaseapp.com",
    projectId: "genpen",
    storageBucket: "genpen.appspot.com",
    messagingSenderId: "892113483794",
    appId: "1:892113483794:web:351f7025e606603054c0cb"
};
//Initialize Firebas
firebase.initializeApp(firebaseConfig);

const auth = firebase.auth();

// redirect to homepage function
const redirect = () => {
    window.location.href = "index.html";
}


// implement all login functions on button click

// github login
loginWithGithub = () => {

    const provider = new firebase.auth.GithubAuthProvider();

    auth.signInWithPopup(provider).then((result) => {
        alert("signed in successfully!");
        redirect();
    }).catch((error) => {
        console.log(error)
    }
    );
}

// google login
loginWithGoogle = () => {

    const provider = new firebase.auth.GoogleAuthProvider();

    auth.signInWithPopup(provider).then((result) => {
        alert("signed in successfully!");
        alert(`Welcome ${result.user.displayName}`);
        redirect();
    }).catch((error) => {
        console.log(error)
    }
    );
}

// facebook login
loginWithFacebook = () => {

    const provider = new firebase.auth.FacebookAuthProvider();

    auth.signInWithPopup(provider).then((result) => {
        alert(`Welcome ${result.user.displayName}`);
        redirect();
    }).catch((error) => {
        console.log(error)
    }
    );
}


// apple login
loginWithApple = () => {

    const provider = new firebase.auth.OAuthProvider('apple.com');

    auth.signInWithPopup(provider).then((result) => {
        alert("signed in successfully!");
        redirect();
    }).catch((error) => {
        console.log(error);
    }
    );
};


//lined in login

