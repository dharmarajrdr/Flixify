import React from 'react'
import UserImage from '../../assets/Dharmaraj.png';
import '../../styles/header/Account.css'

const Account = () => {
    return (
        <div className='flex items-center justify-between' id='account'>
            <img src={ UserImage } />
        </div>
    )
}

export default Account;