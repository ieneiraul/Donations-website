import { useState, useRef, useContext } from "react";
import { useHistory } from "react-router-dom";
import { Redirect } from "react-router-dom";
import AuthContext from "../../store/auth-context";
import classes from "../profile/UpdateForm.module.css";

const DonationCreateForm = () => {
  const history = useHistory();
  const dataImage = new FormData();

  const usernameInputRef = useRef();
  const idDonatorInputRef = useRef();
  const amountDonatedInputRef = useRef();
  const dateInputRef = useRef();

  const [isLoading, setIsLoading] = useState(false);
  const [image, setImage] = useState(null);
  const [isReplaced, setIsReplaced] = useState(false);

  const authCtx = useContext(AuthContext);

  const submitHandler = (event) => {
    event.preventDefault();
    setIsLoading(true);

    // const enteredPassword = passwordInputRef.current.value;
    // const enteredOcupation = ocupationInputRef.current.value;
    // const enteredIban = ibanInputRef.current.value;
    // const enteredPhone = phoneInputRef.current.value;

    // add validation

    const url = "http://localhost:8081/users/" + authCtx.userName;
    const urlPhoto = "http://localhost:8081/users/image/" + authCtx.userName;
    fetch(url, {
      method: "PUT",
      body: JSON.stringify({
        password: enteredPassword,
        ocupation: enteredOcupation,
        iban: enteredIban,
        phone: enteredPhone,
      }),
      headers: {
        "Content-Type": "application/json",
        Authorization: authCtx.token,
      },
    })
      .then((res) => {
        if (res.status === 200) {
          return res.json();
        } else {
          throw new Error("Eroare la modificarea utilizatorului!");
        }
      })
      .then((data) => {
        alert("Ai modificat profilul cu succes!");
      })
      .catch((err) => {
        alert(err.message);
      });
    image && dataImage.append("image", image);
    image &&
      fetch(urlPhoto, {
        method: "POST",
        body: dataImage,
        headers: {
          Authorization: authCtx.token,
        },
      })
        .then((res) => {
          if (res.status === 200) {
            return res.json();
          } else {
            throw new Error(
              "Eroare la modificarea utilizatorului! " + res.status
            );
          }
        })
        .catch((err) => {
          alert(err.message);
        });
    setIsLoading(false);
    // history.replace("/profile");
    setIsReplaced(true);
  };

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(event.target.files[0]);
    }
  };

  return (
    <>{!isReplaced ? 
      <section className={classes.auth}>
        <h2>{"Adaugare donație nouă"}</h2>
        <form onSubmit={submitHandler}>
          <div className={classes.control}>
            <label htmlFor="username">Numele de utilizator al donatorului</label>
            <input type="text" id="username" ref={usernameInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="idDonator">Id-ul donatorului</label>
            <input type="text" id="idDonator" ref={idDonatorInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="needed">Suma donată</label>
            <input type="number" id="needed" ref={amountDonatedInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="startDate">Data</label>
            <input type="date" id="startDate" ref={dateInputRef} />
          </div>
          
          <div className={classes.actions}>
            {!isLoading && <button>{"Adaugă"}</button>}
            {isLoading && <p>Sending request...</p>}
          </div>
        </form>
      </section>
    : <Redirect to ="/" />}
    </>
  );
};

export default DonationCreateForm;