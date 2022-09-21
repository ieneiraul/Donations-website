import "./StoriesFormForProfile.css";
import { useState, useRef, useContext, useEffect } from "react";
import AuthContext from "../../store/auth-context";
import ArrowForwardIosOutlinedIcon from "@mui/icons-material/ArrowForwardIosOutlined";
import ArrowBackIosOutlinedIcon from "@mui/icons-material/ArrowBackIosOutlined";
import StoryCardforProfile from "./StoryCardforProfile";

const StoriesFormForProfile = () => {
  const authCtx = useContext(AuthContext);
  const [stories, setStories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const url = "http://localhost:8081/stories/user/"+authCtx.userName;

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
        setStories(data);
        setIsLoading(false);
      })
      .catch((err) => {
        alert(err.message);
      });
  }, []);


  const carouselRef = useRef()

  const handleClick = (direction) => {
      if (direction === "left") {
          carouselRef.current.style.transform = `translateX(${0}px)`;
      }
      if (direction === "right") {
          carouselRef.current.style.transform = `translateX(${-390}px)`;
      }
  }
  return (
    <div className="storiesGrid2">
      <ArrowBackIosOutlinedIcon
        className="sliderArrowleft"
        onClick={() => handleClick("left")}
      />
      <div className="carouselContainer" ref={carouselRef}>
        {!isLoading &&
          stories.map((story) => (
            <StoryCardforProfile key={story.id} story={story} />
          ))}
      </div>
      <ArrowForwardIosOutlinedIcon
        className="sliderArrowright"
        onClick={() => handleClick("right")}
      />
    </div>
  );
};

export default StoriesFormForProfile;
