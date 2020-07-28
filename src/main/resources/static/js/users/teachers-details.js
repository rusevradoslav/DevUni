const URLS = {
    teachers: '/admins/all-teachers-rest',
};
const toString1 = ({id, fullName, email, username, status}, index) => {

    let firstPart =
        ` <th scope="row">${index}</th>
           <td>${fullName}</td>
           <td>${email}</td>
           <td>${username}</td>`;

    let secondPart = status ?
        `<td>
               <span id="badgeStatus" class="badge badge-success">Active</span>
         </td>`
        :
        `<td>
             <span id="badgeStatusBlocked" class="badge badge-danger">Blocked</span>
         </td>`;

    let thirdPart =
        `    <td>
                <a href="/admins/block-teacher/${id}">Disable</a>
             
                <a href="/admins/activate-teacher/${id}">Enable</a>
               
                <a href="/admins/demote-teacher-student/${id}">Demote To Student</a>

                <a href="/admins/promote-teacher-admin/${id}">Promote To Admin</a>

           </td>`;

    return `<tr>${firstPart}${secondPart}${thirdPart}</tr>`

};
fetch(URLS.teachers)
    .then(response => response.json())
    .then(teachers => {
        let result = '';
        teachers.forEach((teacher, index) => {
            console.log(teacher);
            const adminString = toString1(teacher, index + 1);
            result += adminString;
        });
        document.getElementById('all-teachers')
            .innerHTML = result;

    });