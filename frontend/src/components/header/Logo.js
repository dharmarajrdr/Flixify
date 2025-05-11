import React from 'react'
import '../../styles/header/Logo.css'

const Logo = () => {
  return (
    <a href='/' className='flex items-center'>
        <i className='fa fa-bars' id='leftNavigationIcon' />
        <p id='logoText'>Flixify</p>
    </a>
  )
}

export default Logo