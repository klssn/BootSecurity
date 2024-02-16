let csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content")
let csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content")


// USERS TABLE
document.addEventListener("DOMContentLoaded", fillTableWithUsers)

async function fillTableWithUsers() {
    const tableBody = document.getElementById('usersTable')
    if (!tableBody) {
        return
    }
    let url = '/api/users'
    let users = await getData(url)

    tableBody.innerHTML = ''

    users.forEach(user => {
        let tableUser = `<tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${getUserRoles(user)}</td>
                        <td><button class="btn btn-edit" onclick="callEditModal('${user.id}')">Edit</button></td>
                        <td><button class="btn btn-danger" onclick="callDeleteModal('${user.id}')">Delete</button></td>
                    </tr>`
        tableBody.innerHTML += tableUser
    })
}


// USER TABLE
document.addEventListener("DOMContentLoaded", fillTableWithUser)
async function fillTableWithUser() {
    const tableBody = document.getElementById('userTable')
    if (!tableBody) {
        return
    }
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


// EDIT USER
async function callEditModal(id) {
    let editModal = new bootstrap.Modal(document.getElementById('editUserModal'))
    let url = '/api/users/' + id
    let user = await getData(url)

    document.getElementById('editId').value = user.id
    document.getElementById('editFirstName').value = user.firstName
    document.getElementById('editLastName').value = user.lastName
    document.getElementById('editAge').value = user.age
    document.getElementById('editEmail').value = user.email
    if (user.roles.length > 1) {
        document.getElementById('editRole').value = 'ADMIN'
    } else {
        document.getElementById('editRole').value = 'USER'
    }

    editModal.show()

    let editClickHandler = async (event) => {
        event.preventDefault()

        let editedUser = {
            id: document.getElementById('editId').value,
            firstName: document.getElementById('editFirstName').value,
            lastName: document.getElementById('editLastName').value,
            age: document.getElementById('editAge').value,
            email: document.getElementById('editEmail').value,
            password: document.getElementById('editPassword').value,
            role: document.getElementById('editRole').value
        }

        let url = '/api/users/'
        let method = 'PUT'

        try {
            await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(editedUser)
            })

            editModal.hide()
            await fillTableWithUsers()
        } catch (error) {
            console.log(error)
        }

    }

    document.getElementById('editSubmit').addEventListener('click', editClickHandler)

}


// DELETE USER
let deleteModal = null

async function callDeleteModal(id) {
    try {
        if (!deleteModal) {
            deleteModal = new bootstrap.Modal(document.getElementById('deleteUserModal'))

            let deleteClickHandler = async (event) => {
                event.preventDefault()

                let url = '/api/users/' + document.getElementById('deleteId').value
                let method = 'DELETE'

                try {
                    await fetch(url, {
                        method: method,
                        headers: {
                            'Content-Type': 'application/json',
                            [csrfHeader]: csrfToken
                        }
                    })

                    deleteModal.hide()
                    await fillTableWithUsers()
                } catch (error) {
                    console.log(error)
                }
            }

            document.getElementById('deleteSubmit').addEventListener('click', deleteClickHandler)
        }

        let url = '/api/users/' + id
        let user = await getData(url)

        document.getElementById('deleteId').value = user.id
        document.getElementById('deleteFirstName').value = user.firstName
        document.getElementById('deleteLastName').value = user.lastName
        document.getElementById('deleteAge').value = user.age
        document.getElementById('deleteEmail').value = user.email
        if (user.roles.length > 1) {
            document.getElementById('deleteRole').value = 'ADMIN'
        } else {
            document.getElementById('deleteRole').value = 'USER'
        }

        deleteModal.show()
    } catch (error) {
        console.log(error)
    }
}

/*async function deleteUser(id) {
    let url = '/api/users/' + id
    let method = 'DELETE'

    try {
        await fetch(url, {
            method: method
            })

        await fillTableWithUsers()
    } catch (error) {
        console.log(error)
    }
}*/


// ADD USER
document.addEventListener("DOMContentLoaded", function() {
    let addClickHandler = async (event) => {
        event.preventDefault()

        let newUser = {
            firstName: document.getElementById('addFirstName').value,
            lastName: document.getElementById('addLastName').value,
            age: document.getElementById('addAge').value,
            email: document.getElementById('addEmail').value,
            password: document.getElementById('addPassword').value,
            role: document.getElementById('addRole').value
        }

        let url = '/api/users/'
        let method = 'POST'

        try {
            await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(newUser)
            })

            await fillTableWithUsers()
            clearAddForm()
            document.querySelector('#nav-home-tab').click()

        } catch (error) {
            console.log(error)
        }
    }

    document.getElementById('addSubmit').addEventListener('click', addClickHandler);
});

function clearAddForm() {
    document.getElementById('addFirstName').value = ''
    document.getElementById('addLastName').value = ''
    document.getElementById('addAge').value = ''
    document.getElementById('addEmail').value = ''
    document.getElementById('addPassword').value = ''
    document.getElementById('addRole').selectedIndex = 0
}