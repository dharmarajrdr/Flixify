import React from 'react'
import '../../styles/header/Notification.css'

const Notification = () => {

    const notificationCount = 10;

    return (
        <div id='notification'>
            {notificationCount && <span id='notificationCount'>{notificationCount}</span>}
            <i class="fa fa-bell text-white cursor-pointer" aria-hidden="true" id='notificationIcon'></i>
        </div>
    )
}

export default Notification