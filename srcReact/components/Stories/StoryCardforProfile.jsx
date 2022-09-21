import { useState, useContext, useEffect } from 'react';
import AuthContext from '../../store/auth-context';
import { Link } from "react-router-dom";
import "./StoryCardforProfile.css"

export default function StoryCardforProfile({story}) {

  const authCtx = useContext(AuthContext);
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  const  url = 'http://localhost:8081/users/'+story.authorUsername;

  
  useEffect(()=>{
    fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authCtx.token
      },
    })
      .then((res) => {
         if (res.status === 200 ) {
          return res.json();
        } else {
          
            throw new Error("Eroare la returnarea utilizatorului!");
          }
        
      })
      .then((data) => {
        setUser(data);
        setIsLoading(false);

     })
      .catch((err) => {
        alert(err.message);
      });
      

  },[]);

    return (
        <>
        {!isLoading && 
            <div className="card2">
            <Link to={{pathname:`/stories/${story.id}`, state:{user}}}  key={story.id} style= {{textDecoration: "none"}} >
                <img className="storyImage" src={story.imageUrl} alt='' />
            </Link>
            <label style={{fontWeight:'600', fontSize: '20px'}}>{`${user.lastName} ${user.firstName}`}</label>
            </div>
            }
        </>
    )
}