import hmeImg from "../images/hero4.jpg";
import StoriesForm from "../components/Stories/StoriesForm";
import {  useContext} from 'react';
import AuthContext from '../store/auth-context';

const StoriesPage = () => {
    const authCtx = useContext(AuthContext);

    return (
    <div className="container">
        <header className="jumbotron" style={{
          backgroundImage: `url(${hmeImg})`,
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          backgroundSize: "cover",
          width: '100%',
          
        }}>
        <div  >
            {/* <img src={jumbotronImg}/> */}
            <h1 style= {{
              color: "white",
              fontSize:"30px",
              marginBottom:"30px"
            }}>Doneaza unei cauze si creeaza un viitor mai bun pentru comunitate!</h1>
            <p>{
                authCtx.role=="STUDENT" && <a className="btn btn-primary btn-lg" href="stories/new">Adauga o noua cauza</a>
              }
              {
                authCtx.role=="MANAGER" && <a className="btn btn-primary btn-lg" href="donation/new">Adauga o noua donatie</a>
              }
            </p>
        </div>
      </header>
      <StoriesForm />
    </div>
    );
  };
  
  export default StoriesPage;