import React from 'react'
import UserImage from '../../assets/default_user_image.jpg';
import '../../styles/header/Account.css'

const Account = () => {
    return (
        <div className='flex items-center justify-between px-5 py-2' id='account'>
            <img src={ UserImage } />
        </div>
    )
}

export default Account;