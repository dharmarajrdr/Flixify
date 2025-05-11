import React from 'react'
import '../../styles/navigation/Navigation.css'

const Navigation = () => {
    const buttons = [
        {
            "title": "Home",
            "icon": "fa fa-home",
            "link": "/home",
            "isActive": true
        },
        {
            "title": "Trash",
            "icon": "fa fa-trash",
            "link": "/trash",
            "isActive": false
        },
        {
            "title": "API Doc",
            "icon": "fa fa-book",
            "link": "/api-doc",
            "isActive": false
        }
    ];
    return (
        <div id='navigation'>
            {buttons.map(({ title, link, icon, isActive }) =>
                <a href={link} className={isActive ? 'activeNavigation' : ''}>
                    <i className={icon} />
                    <span>{title}</span>
                </a>
            )}
        </div>
    )
}

export default Navigation