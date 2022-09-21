import { useLocation } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import "./StoryPage.css";
import AuthContext from "../store/auth-context";
import ProgressBar from "../components/ProgressBar";

const StoryPage = () => {
  const location = useLocation();
  const id = location.pathname.split("/")[2];
  const authCtx = useContext(AuthContext);
  const [story, setStory] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [donateClicked, setDonateClicked] = useState(false);

  console.log(location);
  const url = `http://localhost:8081/stories/${id}`;

  useEffect(() => {
    fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: authCtx.token,
      },
    })
      .then((res) => {
        if (res.status === 200) {
          return res.json();
        } else {
          throw new Error("Eroare la returnarea cazurilor!");
        }
      })
      .then((data) => {
        setStory(data);
      })
      .catch((err) => {
        alert(err.message);
      });
    setIsLoading(false);
  }, []);

  const beforeButton = () => {
    return (
      <div className="right">
        <p>{story.text}</p>
        <label className="sumNeeded">{`Suma necesară: ${story.amountNeeded} $`}</label>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => {
            setDonateClicked(true);
          }}
        >
          Donează
        </button>
        <label className="sumGiven">{`Suma strânsă: ${story.amountGiven} $`}</label>
        <ProgressBar
          key={story.id}
          bgcolor="blue"
          completed={((story.amountGiven * 100) / story.amountNeeded).toFixed(
            0
          )}
        />
      </div>
    );
  };
  const afterButton = () => {
    return (
      <div className="right">
        <div className="topTitle">
          <h1>Doneaza pentru</h1>
          <h3>
            {" "}
            {`${location.state.user.lastName} ${location.state.user.firstName}`}{" "}
          </h3>
        </div>
        <div className="labelContainer">
          <label className="backLabel">{`IBAN ${location.state.user.iban}`}</label>
          <label className="backLabel">{`REVOLUT ${location.state.user.phone}`}</label>
          <label className="backLabel">{`EMAIL ${location.state.user.mail}`}</label>
          <label className="backLabel">{`TELEFON ${location.state.user.phone}`}</label>
        </div>
        <button
          className="btn btn-primary btn-lg"
          onClick={() => {
            setDonateClicked(false);
          }}
        >
          Întoarce-te
        </button>
      </div>
    );
  };
  return (
    <>
      {!isLoading && (
        <div className="storyPageContainer">
          <div className="left">
            <div className="imageCont">
              <img
                src={story.imageUrl}
                style={{
                  backgroundSize: "cover",
                  backgroundPosition: "center center",
                }}
              />
            </div>
            <div className="titleContainer">
              <h1>
                {`${location.state.user.lastName} ${location.state.user.firstName}`}
              </h1>
              <h3>
                {location.state.user.ocupation
                  ? location.state.user.ocupation
                  : location.state.user.role}
              </h3>
            </div>
          </div>
          {!donateClicked ? beforeButton() : afterButton()}
        </div>
      )}
    </>
  );
};

export default StoryPage;
