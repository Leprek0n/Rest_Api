$(document).ready(function () {
    viewUserInfo();
});

async function viewUserInfo() {
    fetch("http://localhost:8080/users/user/principal")
        .then((res) => res.json())
        .then((user) => {
            $(".nav-username").text(user.username);
            $(".nav-user-roles").text('with roles: ' + user.roles.map(r => r.role.replace('ROLE_', '')).join(', '));

            let tab = "";
            tab += `
            <tr>
            <td> ${user.id}</td>
            <td> ${user.firstName}</td>
            <td> ${user.lastName}</td>
            <td> ${user.age}</td>
            <td> ${user.username}</td>
            <td> ${user.roles.map(r => r.role.replace('ROLE_', '')).join(', ')}</td>
            </tr>`;

            $('#data').append(tab);
        })

}