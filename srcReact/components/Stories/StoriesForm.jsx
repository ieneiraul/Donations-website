import "./StoriesForm.css"
import { useState, useContext, useEffect } from 'react';
import AuthContext from '../../store/auth-context';
import StoryCard from './StoryCard';



const StoriesForm = () => {
  
  const authCtx = useContext(AuthContext);
  const [stories, setStories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const  url = 'http://localhost:8081/stories';

  
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
          
            throw new Error("Eroare la returnarea cazurilor!");
          }
        
      })
      .then((data) => {
        setStories(data);
        setIsLoading(false);
     })
      .catch((err) => {
        alert(err.message);
      });
      
  },[]);

  return (
    <div className="storiesGrid">
      {!isLoading && stories.map((story)=>(
        <StoryCard key={story.id} story={story}/>
      ))}
     </div>
  );
};

export default StoriesForm;