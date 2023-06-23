function editUserInfo() {
    const editButtom = document.getElementById('edit');
    const saveButtom = document.getElementById('save');
    const userInfo = [];
    const login = document.getElementById('login');
    const name = document.getElementById('name');
    const lastname = document.getElementById('lastname');
    const email = document.getElementById('email');
    userInfo.push(name, lastname, email, login)
    userInfo.forEach((element) => {
        element.classList.add('placeHolderColor');
        element.disabled = false;
        element.style.backgroundColor = "lightgray"
        element.focus()
    });
    switchDisabled(editButtom, saveButtom)
}

function switchDisabled(disabled, enabled) {
    disabled.disabled = true;
    enabled.disabled = false;
}