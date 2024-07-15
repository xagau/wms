


const loginWithLinkedIn = () => {
    console.log("login called!");
    const CLIENT_ID = "78t22i5ud000xt";
    const CLIENT_SECRET = "Xdq6Z0aNzL0GGdAZ";
    const REDIRECT_URI = "https://genpen.io/redirect.html";
    const STATE = 3923748742938;

    // open window
    const url = `https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&state=${STATE}&scope=r_liteprofile%20r_emailaddress`
    const width = 450;
    const height = 730;
    const left = (window.innerWidth / 2) - (width / 2);


    const popup = window.open(url, '', `width=${width}, height=${height}, top=0, left=${left}`);

    // listen for message from redirect.html
    window.addEventListener('message', async ({ data }) => {
        popup.close();
        console.log(data);
        if (data.error) {
            console.log(data.error);
        } else {
            alert("Successfully logged in!")

            const corsProxyUrl = "https://cors-anywhere.herokuapp.com/";

            // get access token
            const code = data.code;
            const authUrl = `${corsProxyUrl}https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=${code}&redirect_uri=${REDIRECT_URI}&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}`;

            const authResponse = await fetch(authUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });
            const authJson = await authResponse.json();
            // const accessToken = authJson.access_token;

            // use access token to fetch user info
            // const userUrl = `${corsProxyUrl}https://api.linkedin.com/v2/me`;
            // const userResponse = await fetch(userUrl, {
            //     method: "GET",
            //     headers: {
            //         "Authorization": `Bearer ${accessToken}`
            //     }
            // });
            // const userJson = await userResponse.json();
            // console.log(userJson);

        }
    }, false);
}