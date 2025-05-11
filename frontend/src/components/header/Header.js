import React from 'react'
import Logo from './Logo'
import '../../styles/header/Header.css'
import GetStarted from './GetStarted'
import Account from './Account'
import Notification from './Notification'

const Header = () => {

    const isLoggedIn = true;

    return (
        <div className='flex items-center justify-between' id='header'>
            <Logo />
            {isLoggedIn ?
                <div className='flex items-center justify-between py-2'>
                    <Notification />
                    <Account />
                </div> :
                <GetStarted />}
        </div>
    )
}

export default Header