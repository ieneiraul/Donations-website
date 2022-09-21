import { useState, useRef, useContext } from 'react';
import { useHistory } from 'react-router-dom';

import AuthContext from '../../store/auth-context';
import classes from './AuthForm.module.css';



const RegisterForm = () => {
  const history = useHistory();
  const firstnameInputRef = useRef();
  const lastnameInputRef = useRef();
  const usernameInputRef = useRef();
  const emailInputRef = useRef();
  const passwordInputRef = useRef();
  const roleInputRef = useRef();

  const authCtx = useContext(AuthContext);


  const [isLoading, setIsLoading] = useState(false);


  const submitHandler = (event) => {
    event.preventDefault();

    const enteredFirstname = firstnameInputRef.current.value;
    const enteredLastname = lastnameInputRef.current.value;
    const enteredUsername = usernameInputRef.current.value;
    const enteredEmail = emailInputRef.current.value;
    const enteredPassword = passwordInputRef.current.value;
    const enteredRole = roleInputRef.current.checked;
    let userRole;

    if(enteredRole=== false) {
      console.log("d");
      userRole="DONATOR";
    }
    else {
      console.log("s");
      userRole="STUDENT";
    }

    function myFunction() {
      var checkBox = document.getElementById("myCheck");
      var text = document.getElementById("text");
      if (checkBox.checked == true){
        text.style.display = "block";
      } else {
         text.style.display = "none";
      }
    }

    // optional: Add validation

    setIsLoading(true);
    let url = 'http://localhost:8081/registration';
      
    fetch(url, {
      method: 'POST',
      body: JSON.stringify({
        firstName : enteredFirstname,
        lastName : enteredLastname,
        userName: enteredUsername,
        mail: enteredEmail,
        password: enteredPassword,
        role: userRole
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
        if(!data.response.startsWith('Confirmation'))  throw new Error(data.response);
        console.log(!data.response.startsWith('Confirmation'));
        alert(data.response);
        history.replace('/login');
      })
      .catch((err) => {
        alert(err.message);
      });
  };

  return (
    <section className={classes.auth}>
      <h1>{'Sign Up'}</h1>
      <form onSubmit={submitHandler}>

        <div className={classes.control}>
          <label htmlFor='firstname'>Prenume*</label>
          <input type='text' id='firstname' required ref={firstnameInputRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor='lastname'>Nume*</label>
          <input type='text' id='lastname' required ref={lastnameInputRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor='username'>Username*</label>
          <input type='text' id='username' required ref={usernameInputRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor='email'>Email*</label>
          <input type='email' id='email' required ref={emailInputRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor='password'>Parola*</label>
          <input
            type='password'
            id='password'
            required
            ref={passwordInputRef}
          />
        </div>
        <div className={classes.radio}>
          <p>Rolul tău:</p>
          <label htmlFor="student">student&nbsp;&nbsp;</label>
          <input type="radio" id="student" name="role" value="STUDENT" className={classes.checkmark} required ref={roleInputRef} ></input>
          <span>&nbsp;&nbsp;&nbsp;</span>
          <label htmlFor="donator">&nbsp;donator&nbsp;&nbsp;</label>
          <input type="radio" id="donator" name="role" value="DONATOR" className={classes.checkmark} ></input>
        </div>
        <div className={classes.actions}>
          {!isLoading && (
            <button>{ 'Inregistrare'}</button>
          )}
          {isLoading && <p>Sending request...</p>}
          
        </div>
      </form>
    </section>
  );
};

export default RegisterForm;