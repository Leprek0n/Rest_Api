$(document).ready(function () {
    viewAllUsers();
});

//----------------------Fill modal----------------------------------

function getUserForFillModal(id) {

    fetch("http://localhost:8080/users/admin/" + id).then(
        res => {
            res.json().then(
                user => {
                    console.log(user);
                    //----------fillEditModal------------------------
                    $(".modal-body #idEdit").val(user.id)
                    $(".modal-body #firstNameEdit").val(user.firstName)
                    $(".modal-body #lastNameEdit").val(user.lastName)
                    $(".modal-body #ageEdit").val(user.age)
                    $(".modal-body #usernameEdit").val(user.username)
                    $('#rolesEdit').val(user.roles.map(r => r.role.replace('ROLE_', '')).join(', '))

                    //    -------------fillDeleteModal-----------------------
                    $(".modal-body #idDelete").val(user.id)
                    $(".modal-body #firstNameDelete").val(user.firstName)
                    $(".modal-body #lastNameDelete").val(user.lastName)
                    $(".modal-body #ageDelete").val(user.age)
                    $(".modal-body #usernameDelete").val(user.username)
                    $('#rolesDelete').val(user.roles.map(r => r.role.replace('ROLE_', '')).join(', '))

                })
        });
}

//-------------------Admin panel with all users (tab)---------------

async function viewAllUsers() {

    fetch("http://localhost:8080/users/admin/principal")
        .then((res) => res.json())
        .then((user) => {
            $(".nav-username").text(user.username);
            $(".nav-user-roles").text('with roles: ' + user.roles.map(r => r.role.replace('ROLE_', '')).join(', '));


        })


    $('#data').empty();

    fetch("http://localhost:8080/users/admin/allusers")
        .then((res) => res.json())
        .then((data) => {
            let tab = "";
            data.forEach(function (user) {
                tab += `
                    <tr>
                        <td id="id${user.id}">${user.id}</td>
                        <td id='firstName${user.id}'>${user.firstName}</td>
                        <td id='lastName${user.id}'>${user.lastName}</td>
                        <td id='age${user.id}'>${user.age}</td>
                        <td id='username${user.id}'>${user.username}</td>
                        <td id='roles${user.id}'>${user.roles.map(r => r.role.replace('ROLE_', '')).join(', ')}</td>
                        <td>
                            <button onclick= "getUserForFillModal('${user.id}')" class="btn btn-primary"
                                type="button"
                                data-bs-toggle="modal"
                                data-bs-target="#edit"
                                style="color: white; background-color: #2996ac; border-color:#2996ac"
                                > Edit
                        </button>
                        </td>
                        <td>
                            <button onclick="getUserForFillModal('${user.id}')"
                                    class="btn btn-primary"
                                    type="button" 
                                    data-bs-toggle="modal"
                                    data-bs-target="#delete"
                                    style="background-color: red; border-color: red"
                            >Delete
                            </button>
                        </td>
                    </tr>`;
            });
            $('#data').append(tab);
        })
}

//-------------------User INFO--------------------------------------

function userInfo() {
    $("#userInfoBodyTab").empty();
    $("#headPanel").css("display", "none");
    fetch("http://localhost:8080/users/admin/principal")
        .then((res) => res.json())
        .then((user) => {
            let tab = "<tr>";
            tab += `
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.username}</td>
            <td>${user.roles.map(r => r.role.replace('ROLE_', '')).join(', ')}</td>
            `;
            tab += "<tr>";
            $('#userInfoBodyTab').append(tab);
        })

}

function allUser() {
    $("#headPanel").css("display", "block")
}

//-------------Update User--------------------------------------------


async function updateUser() {
    event.preventDefault()


    let userId = $("#idEdit").val();
    let firstName = $("#firstNameEdit").val();
    let lastName = $("#lastNameEdit").val();
    let age = $("#ageEdit").val();
    let username = $("#usernameEdit").val();
    let password = $("#passwordEdit").val();
    let roles = (document.getElementById("rolesEdit")).value;

    let user = {
        firstName: firstName,
        lastName: lastName,
        age: age,
        username: username,
        password: password,
        roles: roles
    }

    $(".error").remove();
    if (firstName.length < 2 || firstName.length > 30) {
        $('#firstNameEdit').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
    } else if (lastName.length < 2 || lastName.length > 30) {
        $('#lastNameEdit').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
    } else if (age < 1) {
        $('#ageEdit').after('<span class="error">This field is required. The age cannot be less than 1!</span>');
    } else if (username.length < 2 || username.length > 30) {
        $('#usernameEdit').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
    }
    else if ((password.length < 5 &&  password.length > 0) || password.length > 100) {
        $('#passwordEdit').after('<span class="error">This field is required. Minimum 5 and maximum 30 characters!</span>');
    }
    else if (roles.length < 1) {
        $('#rolesEdit').after('<span class="error">This field is required</span>');
    } else {
        let response = await fetch("http://localhost:8080/users/admin/" + userId +"/" + username, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });
        if (response.ok) {
            $('#edit').modal('hide');
            viewAllUsers();
        } else {
            $(".error").remove();
            $('#usernameEdit').after('<span class="error">Username is exist</span>');
        }

    }

}


//----------------DELETE User-----------------------------------------

function deleteUser() {

    fetch("http://localhost:8080/users/admin/delete/" + $("#idDelete").val(), {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    viewAllUsers();
}

//----------------NEW User--------------------------------------------


$(document).ready(function () {
    $('#addUser').submit(async function (event) {
            event.preventDefault();
            let firstName = $("#firstNameNewUser").val();
            let lastName = $("#lastNameNewUser").val();
            let age = $("#ageNewUser").val();
            let username = $("#usernameNewUser").val();
            let password = $("#passwordNewUser").val();
            let roles = (document.getElementById("rolesNewUser")).value;

            let user = {
                firstName: firstName,
                lastName: lastName,
                age: age,
                username: username,
                password: password,
                roles: roles
            }

            $(".error").remove();
            if (firstName.length < 2 || firstName.length > 30) {
                $('#firstNameNewUser').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
            } else if (lastName.length < 2 || lastName.length > 30) {
                $('#lastNameNewUser').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
            } else if (age < 1) {
                $('#ageNewUser').after('<span class="error">This field is required. The age cannot be less than 1!</span>');
            } else if (username.length < 2 || username.length > 30) {
                $('#usernameNewUser').after('<span class="error">This field is required. Minimum 2 and maximum 30 characters!</span>');
            } else if (password.length < 5 || password.length > 100) {
                $('#passwordNewUser').after('<span class="error">This field is required. Minimum 5 and maximum 30 characters!</span>');
            } else if (roles.length < 1) {
                $('#rolesNewUser').after('<span class="error">This field is required</span>');
            } else {
                let response = await fetch("http://localhost:8080/users/admin/newUser", {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(user)
                });
                if (response.ok) {
                    viewAllUsers();
                    $("#users").addClass("show active")
                    $("#addUser").removeClass("active")
                } else {
                    $(".error").remove();
                    $('#usernameNewUser').after('<span class="error">Username is exist</span>');
                }


            }
        }
    )

})
