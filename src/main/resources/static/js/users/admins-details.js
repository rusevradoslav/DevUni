const URLS = {
    admins: '/admins/all-admins-rest',
};
const toString1 = ({id, fullName, email, username,status},index) => {

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

    console.log(status);
    let thirdPart =
        `    <td>
               <a href="/admins/block-admin/${id}">Disable</a>
               &nbsp;&nbsp;&nbsp;
               <a href="/admins/activate-admin/${id}">Enable</a>

               <a href="/admins/demote-admin-student/${id}">Demote To Student</a>

               <a href="/admins/demote-admin-teacher/${id}">Demote To Teacher</a>

           </td>`;

    return `<tr>${firstPart}${secondPart}${thirdPart}</tr>`

};

fetch(URLS.admins)
    .then(response => response.json())
    .then(admins => {
        let result = '';
        admins.forEach((admin,index) => {

            const adminString = toString1(admin,index+1);
            result += adminString;
        });
        document.getElementById('all-admins')
            .innerHTML = result;

    });

