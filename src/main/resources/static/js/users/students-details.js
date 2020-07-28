const URLS = {
    students: '/admins/all-students-rest',
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


    let thirdPart =
        `    <td>
                <a href="/admins/block-student/${id}">Disable</a>
         
                <a href="/admins/activate-student/${id}">Enable</a>
    
                <a href="/admins/promote-student-teacher/${id}">Promote To Teacher</a>

                <a href="/admins/promote-student-admin/${id}">Promote To Admin</a>

           </td>`;

    return `<tr>${firstPart}${secondPart}${thirdPart}</tr>`

};
fetch(URLS.students)
    .then(response => response.json())
    .then(students => {
        let result = '';
        students.forEach((student,index) => {
            console.log(student);
            const adminString = toString1(student,index+1);
            result += adminString;
        });
        document.getElementById('all-students')
            .innerHTML = result;

    });