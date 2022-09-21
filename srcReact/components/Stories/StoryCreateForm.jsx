import { useState, useRef, useContext } from "react";
import { useHistory } from "react-router-dom";
import { Redirect } from "react-router-dom";
import AuthContext from "../../store/auth-context";
import classes from "../profile/UpdateForm.module.css";

const StoryCreateForm = () => {
  const history = useHistory();
  const dataImage = new FormData();

  const textInputRef = useRef();
  const startDateInputRef = useRef();
  const endDateInputRef = useRef();
  const amountNeededInputRef = useRef();

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
        <h2>{"Adaugare caz nou"}</h2>
        <form onSubmit={submitHandler}>
          <div className={classes.control}>
            <label htmlFor="text">Descriere caz</label>
            <textarea id="text" rows="4" cols="40" ref={textInputRef}/>
          </div>
          <div className={classes.control}>
            <label htmlFor="startDate">Data de inceput</label>
            <input type="date" id="startDate" ref={startDateInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="endDate">Data de finalizare</label>
            <input type="date" id="endDate" ref={endDateInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="needed">Suma necesară</label>
            <input type="number" id="needed" ref={amountNeededInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor="image">Selectează o imagine</label>
            <div className="custom-file">
              <input
                type="file"
                className="custom-file-input"
                onChange={onImageChange}
                name="user[profileImage]"
                id="image"
              />
              <label className="custom-file-label" htmlFor="image">
                Incarcă o imagine de profil
              </label>
            </div>
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

export default StoryCreateForm;
