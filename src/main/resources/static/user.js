
// USER TABLE
document.addEventListener("DOMContentLoaded", fillTableWithUser)
async function fillTableWithUser() {
    const tableBody = document.getElementById('userTable')

    let url = '/api/user/users/currentUser'
    let user = await getData(url)

    tableBody.innerHTML = ''

    let tableUser = `<tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${getUserRoles(user)}</td>
                </tr>`
    tableBody.innerHTML = tableUser
}

async function getData(url){
    try {
        let response = await fetch(url)
        if (!response.ok) {
            console.log(response)
            return
        }
        return response.json()
    }
    catch (error) {
        console.log(error)
    }
}

function getUserRoles(user) {
    let userRoles = ""
    user.roles.forEach(role => {
        userRoles += role.role + " "
    })
    return userRoles
}