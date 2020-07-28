const URLS = {
    teachers_requests: '/admins/all-teachers-requests-rest',
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
                  <a href="/admins/confirm-teacher-request/${id}">Confirm</a>
             
                <a href="/admins/cancel-teacher-request/${id}">Cancel</a>

           </td>`;

    return `<tr>${firstPart}${secondPart}${thirdPart}</tr>`

};
fetch(URLS.teachers_requests)
    .then(response => response.json())
    .then(teachersWithRequests => {
        console.log(teachersWithRequests)
        let result = '';
        teachersWithRequests.forEach((teacher, index) => {
            console.log(teacher);
            const adminString = toString1(teacher, index + 1);
            result += adminString;
        });
        document.getElementById('all-teachers-requests')
            .innerHTML = result;

    });