$(document).ready(function () {
    updateTable();
});

function updateTable() {

    $("#mainBody").empty();
    //document.querySelector("#mainBody").innerHTML="";

    $.ajax({
        url: "/rest/users",
        type: "GET",
        dataType: "json",
        success: function (data) {
            const users = JSON.parse(JSON.stringify(data));

            for (let user of users) {
                var myArray = new Array();
                for (authGroupP of user.authGroupList) {
                    myArray.push(authGroupP.authGroup)
                }
                var id = user.id;
                $("#mainBody").append("<tr>" +
                    "<td>" + user.id + "</td>" +
                    "<td>" + user.firstName + "</td>" +
                    "<td>" + user.lastName + "</td>" +
                    "<td>" + user.age + "</td>" +
                    "<td>" + user.email + "</td>" +
                    "<td>" + myArray.join(" ") + "</td>" +
                    "<td><button onclick='editUser(" + user.id + ")' type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#EDIT\">Edit</button></td>" +
                    "<td><button onclick='deleteUser(" + user.id + ")' type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#DELETE\">Delete</button></td>" +
                    "<tr>"
                );
            }
        },
    });
}

function deleteUser(id) {
    $.ajax({
        type: "GET",
        url: "/rest/user/" + id,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            const user = JSON.parse(JSON.stringify(data))

            $("#idDel").append().val(user.id);
            $("#firstNameDel").append().val(user.firstName);
            $("#lastNameDel").append().val(user.lastName);
            $("#ageDel").append().val(user.age);
            $("#emailDel").append().val(user.email);
            let checkUser = false;
            for (let authGroupP of user.authGroupList) {
                if (authGroupP.authGroup == "ADMIN") {
                    checkUser = true;
                }
            }
            if (checkUser) {
                $("#roleDel").prop("admin", true)
            } else {
                $("#roleDel").prop("user", true);
            }
        }
    });
}

function deleteByID() {
    var id = $("#idDel").val();
    $.ajax({
        type: "DELETE",
        url: "/rest/user/" + id,
        contentType: "application/json",
        success: function () {
            updateTable();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
            alert(errorThrown);
        }
    });
}

function editUser(id) {

    $.ajax({
        type: "GET",
        url: "/rest/user/" + id,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            const user = JSON.parse(JSON.stringify(data))
            $("#idEdit").append().val(user.id);
            $("#firstNameEdit").append().val(user.firstName);
            $("#lastNameEdit").append().val(user.lastName);
            $("#ageEdit").append().val(user.age);
            $("#emailEdit").append().val(user.email);
            $("#passwordEdit").append().val(user.password);


            if (user.authGroupList.length > 1) {
                $("#roleEdit").val("admin")
            } else {

                $("#roleEdit").val("user");
            }
        }
    });
};

function updateUser() {
    var roles = [];

    if ($("#roleEdit").val() == "admin") {
        roles.push({name: $("#emailEdit").val(), authGroup: "USER"})
        roles.push({name: $("#emailEdit").val(), authGroup: "ADMIN"})
    } else {
        roles.push({name: $("#emailEdit").val(), authGroup: "USER"})
    }


    var user = {
        id: $("#idEdit").val(),
        firstName: $("#firstNameEdit").val(),
        lastName: $("#lastNameEdit").val(),
        age: $("#ageEdit").val(),
        email: $("#emailEdit").val(),
        password: $("#passwordEdit").val(),
        authGroupList: roles
    }

    $.ajax({
        url: "/rest/user",
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(user),
        contentType: "application/json",
        success: function () {
            updateTable();
        }
    })
}

/*function jsAddUser() {

    let rolesNew = [];

    if ($("#roleNew").val() == "admin") {
        rolesNew.push({name: $("#emailNew").val(), authGroup: "ADMIN"});
        rolesNew.push({name: $("#emailNew").val(), authGroup: "USER"});

    } else {
        rolesNew.push({name: $("#emailNew").val(), authGroup: "USER"});
    }

    let userNew = {

        firstName: $("#firstNameNew").val(),
        lastName: $("#lastNameNew").val(),
        age: $("#ageNew").val(),
        email: $("#emailNew").val(),
        password: $("#passwordNew").val(),
        authGroupList: rolesNew
    };


    $.ajax({
        url: "/rest/user",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(userNew),
        success: function () {
            alert("Saved");
            updateTable();
            $('#userTab').addClass("active");
            $('#table').addClass("show active");
            $('#newUser').removeClass("show active");
            $('#userNewTab').removeClass("active");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
            alert(errorThrown);
            console(errorThrown)
        }
    });

}*/

$(document).on('submit', 'form#newUserForm', function (e) {
    e.preventDefault();
    let rolesNew = [];

    if ($("#roleNew").val() == "admin") {
        rolesNew.push({id: null, name: $("#emailNew").val(), authGroup: "ADMIN"});
        rolesNew.push({id: null, name: $("#emailNew").val(), authGroup: "USER"});

    } else {
        rolesNew.push({name: $("#emailNew").val(), authGroup: "USER"});
    }

    let userNew = {
        id: null,
        firstName: $("#firstNameNew").val(),
        lastName: $("#lastNameNew").val(),
        age: $("#ageNew").val(),
        email: $("#emailNew").val(),
        password: $("#passwordNew").val(),
        authGroupList: rolesNew
    };
    $.ajax({
        url: "/rest/user",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(userNew),
        success: function () {
            updateTable();
            $('#userTab').addClass("active");
            $('#table').addClass("show active");
            $('#newUser').removeClass("show active");
            $('#userNewTab').removeClass("active");

            $("#firstNameNew").val("");
            $("#lastNameNew").val("");
            $("#ageNew").val("");
            $("#emailNew").val("");
            $("#passwordNew").val("");
            $("#roleNew").val("");


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
            alert(errorThrown);

        }
    });

})




