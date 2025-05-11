import React from 'react'

const GetStarted = () => {
    return (
        <div className='flex items-center justify-between px-5 py-2'>
            <div className='mx-4 cursor-pointer transition duration-300 ease-in-out rounded-full px-4 py-2'>
                <span className='text-white'>Log In</span>
            </div>
            <div className='border-2 border-white rounded-full px-6 py-2 cursor-pointer transition duration-300 ease-in-out'>
                <span className='text-white'>Sign Up Free</span>
            </div>
        </div>
    )
}

export default GetStarted