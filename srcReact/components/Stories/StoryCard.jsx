import  "./StoryCard.css";
import { useState, useContext, useEffect } from 'react';
import AuthContext from '../../store/auth-context';
import { Link } from "react-router-dom";

export default function StoryCard({story}) {

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
        {!isLoading && <div className='gridCard'>
            <Link to={{pathname:`/stories/${story.id}`, state:{user}}}  key={story.id} style= {{textDecoration: "none"}} >
                <div className="centerImage">
                  <img src={story.imageUrl} alt='' />
                </div>
                <div className="titleContainer">
                    <h1> {`${user.lastName} ${user.firstName}`}</h1>
                    <h3>{user.ocupation ? user.ocupation : user.role}</h3>
                </div>
                <div className='desc' style= {{textDecoration: "none"}}>
                    <p>{story.text}</p>
                </div>
            </Link>
        </div>}
        </>
    )
}