import { useState, useContext, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import StoriesForm from '../Stories/StoriesForm';
import AuthContext from '../../store/auth-context';
import classes from './ProfileForm.module.css';
import StoriesFormForProfile from '../Stories/StoriesFormForProfile';


const ProfileForm = () => {
  const history = useHistory();
  const authCtx = useContext(AuthContext);
  const [user, setUser] = useState(null);

  const  url = 'http://localhost:8081/users/'+authCtx.userName;

  
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

     })
      .catch((err) => {
        alert(err.message);
      });
  },[]);

  const deleteHandler = (event)=>{
    event.preventDefault();

    if (confirm("Chiar doresti sa stergi acest cont?")) {
      fetch('http://localhost:8081/users/'+user.id, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': authCtx.token
        },
      })
        .then((res) => {
           if (res.status === 200 ) {
            return res.json();
          } else {
            
              throw new Error("Eroare la stergerea utilizatorului!");
            }
          
        })
        .then((data) => {
          alert(data.response);
          history.replace('/login');
          authCtx.logout();
       })
        .catch((err) => {
          alert(err.message);
        });
        
    } 
  }

  return (
    <div className="container">
    <div className="row d-flex justify-content-center">
        <div className="col-md-10 col-sm-12 mt-5 shadow">
            <div className="row">
                <div className="col-sm-4 bg-dark rounded-left d-flex justify-content-center">
                    <div className="card-body text-center text-white">
                      {((user)&&(user.imageUrl))?<img className={ classes.profileImage + " img-responsive rounded-circle"} src={user.imageUrl} alt="..."/>
                      :<img className={ classes.profileImage + " img-responsive rounded-circle"} src="https://res.cloudinary.com/dtb67cyxp/image/upload/v1661635441/dreamsForFuture/empty-avatar-01_1_ludlc3.jpg" alt="..."/>}
                        <h2 className="font-weight-bold mt-4">
                            {(user)?""+ user.userName: ""}
                        </h2>
                        {
                          ((user)&& (user.role=="STUDENT" || user.role=="DONATOR"))
                          ? <div>
                              <a href="/profile/edit"><i className="far fa-edit fa-2x mb-4"></i></a>
                              <form className="deleteForm" >
                                  <button id="submit" className="btn" onClick={deleteHandler}  ><i className={classes.deleteBtn + " fas fa-user-slash fa-2x mb-4" }></i></button>
                              </form>
                              <div id="spinner" className="text-info mx-auto d-block"></div>
                            </div>
                          : <div></div>  
                            } 
                    </div>
                </div>
                <div className="col-sm-8 bg-white rounded-right"
                 style={{overflow:'hidden'}}
                 >
                    {(user)
                    ? <div>
                        <h2 className="mt-3 text-center font-weight-bold">{user.lastName+" " + user.firstName}</h2>
                        <h3 className="mt-3 mb-5 text-center font-weight-bold">{user.role}</h3>
                        <h3 className='text-left ml-5'>Mail: {user.mail}</h3>
                        <h3 className='text-left ml-5'>Iban: {user.iban}</h3>
                        <h3 className='text-left ml-5'>Telefon: {user.phone}</h3>
                        <h3 className='mt-3 text-center font-weight-bold' style={{marginTop:'30px'}}>Anunțurile tale:</h3>
                      </div>
                    : <div className="text-info mx-auto d-block spinner spinner-border"></div>
                  }
                  <StoriesFormForProfile/>
                    {/* <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Ex iusto minima commodi amet molestiae neque sequi voluptatum nemo consequatur voluptatem explicabo id, beatae, sit earum temporibus in, veniam dolorem impedit.</p> */}
                    {/* <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Ea, tempora culpa. Fugiat exercitationem dolorum facilis? Quam soluta architecto dolore obcaecati, voluptatum ea expedita, possimus atque perspiciatis, repudiandae earum excepturi deserunt.</p> */}
                </div>
        
            </div>
        </div>
    </div>
</div>
  );
};

export default ProfileForm;