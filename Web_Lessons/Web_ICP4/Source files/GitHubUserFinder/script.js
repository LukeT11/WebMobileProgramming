/*JavaScript for GitHub Use Finder*/

/*Function to get user using GitHub API with XMLHttpRequest and Get request*/
function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)

    const url = "https://api.github.com/users/"+user;
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open('GET', url, false);
    xmlhttp.send();
    return xmlhttp;
}

/*Function that displays contents for html gathered on user from the GitHub API*/
function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    $('#profileUsername').text(user.login);
    $('.profile').css('display', 'block');
    $('#avatar').html('<img id="avatarImg" src="' + user.avatar_url + '" alt="avatarImage">');
    $('#profileId').text("ID: " + user.id);
    if (user.name === null)
    {
        $('#name').text("Name: N/A");
    }
    else {
        $('#name').text("Name: " + user.name);
    }

    $('#link').html('<span>User Link: </span><a id="html_url" href="' + user.html_url + '" target="_blank" >' + user.html_url + '</a>');
}

/*Function for displays for html that the searched user couldn't be found*/
function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    $('#notFound').text("* User '" + username + "' Not Found");
}

/*Function that takes typed user to be searched using JSON and jQuery*/
$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if enter(i.e. return) key is pressed
        if (e.which == 13) {
            //Clear previous searched user info.
            $('.profile').css('display', 'none');
            $('#profileUsername').text("");
            $('#profileId').text("");
            $('#avatar').html("");
            $('#name').text("");
            $('#html_url').text("");
            $('#notFound').text("");
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the response
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
