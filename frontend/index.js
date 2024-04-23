const config = {
    auth: {
        clientId: 'b5a0f0e5-e9be-4880-9eaa-f9eb9397b818',
        authority: 'https://login.microsoftonline.com/consumers/',
        redirectUri: 'https://d1xb965e89guo1.cloudfront.net/index.html', 
        postLogoutRedirectUri: 'https://d1xb965e89guo1.cloudfront.net/login.html' 
    },
    cache: {
        cacheLocation: 'localStorage',
      }
};

const client = new Msal.UserAgentApplication(config);

const request = {
    scopes: [ 'user.read' ]
};
async function signIn() {
    
    let loginResponse = await client.loginPopup(request);
    if(loginResponse){
        localStorage.setItem('mail', loginResponse.idToken.preferredName);
        localStorage.setItem('token', loginResponse.idToken.rawIdToken);
        location.href = "https://d1xb965e89guo1.cloudfront.net/index.html";
    }
    
}

async function signOut(){
    client.logout();
}