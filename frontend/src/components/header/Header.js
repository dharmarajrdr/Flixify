import React from 'react'
import Logo from './Logo'
import '../../styles/header/Header.css'
import GetStarted from './GetStarted'
import Account from './Account'
import Notification from './Notification'
import Navigation from '../navigation/Navigation'

const Header = () => {

    const isLoggedIn = true;

    return (
        <div className='flex items-center justify-between' id='header'>
            <Logo />
            {isLoggedIn ?
                <div className='flex items-center py-2 FRCB w100'>
                    <div>
                        <Navigation />
                    </div>
                    <div className='FRCB'>
                        <Notification />
                        <Account />
                    </div>
                </div> :
                <GetStarted />}
        </div>
    )
}

export default Header