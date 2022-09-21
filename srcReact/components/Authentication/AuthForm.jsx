import { useState, useRef, useContext } from 'react';
import { useHistory } from 'react-router-dom';

import AuthContext from '../../store/auth-context';
import classes from './AuthForm.module.css';





const AuthForm = () => {
 
  const history = useHistory();
  const emailInputRef = useRef();
  const passwordInputRef = useRef();

  const authCtx = useContext(AuthContext);

  const [isLoading, setIsLoading] = useState(false);

  const submitHandler = (event) => {
    event.preventDefault();

    const enteredEmail = emailInputRef.current.value;
    const enteredPassword = passwordInputRef.current.value;

    // optional: Add validation

    setIsLoading(true);
    let  url = 'http://localhost:8081/login';
   
    fetch(url, {
      method: 'POST',
      body: JSON.stringify({
        username: enteredEmail,
        password: enteredPassword
      }),
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
        console.log(res);
        setIsLoading(false);
        if (res.status === 200) {
          let resJson =res.json();
          console.log(resJson);
          return resJson;
        } else {
          return res.json().then((data) => {
            let errorMessage = 'Authentication failed!';
            // if (data && data.error && data.error.message) {
            //   errorMessage = data.error.message;
            // }

            throw new Error(errorMessage);
          });
        }
      })
      .then((data) => {
         const expirationTime = new Date(
           new Date().getTime() + 2 *1440 * 60000 // expires in 2 days
         );
        authCtx.login(data.token, enteredEmail, data.user.role, expirationTime.toISOString());
        history.replace('/stories');
        alert("Te-ai conectat cu succes!");
        // <Alert severity="success">This is a success alert — check it out!</Alert>
        // console.log("da da");
      })
      .catch((err) => {
        alert("Eroare la autentificare!");
      });
  };


  return (
    <section className={classes.auth}>
      <h1>{'Login'}</h1>
      <form onSubmit={submitHandler}>
        <div className={classes.control}>
          <label htmlFor='email'>Your Email</label>
          <input type='text' id='email' required ref={emailInputRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor='password'>Your Password</label>
          <input
            type='password'
            id='password'
            required
            ref={passwordInputRef}
          />
        </div>
        <div className={classes.actions}>
          {!isLoading && (
            <button>{ 'Login'}</button>
          )}
          {isLoading && <p>Sending request...</p>}
          
        </div>
      </form>
    </section>
  );
};

export default AuthForm;