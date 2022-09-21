import hmeImg from "../../images/Dad-Hero.png";
import React from "react";
import { Button } from "@mui/material";
import { Link } from 'react-router-dom';

export const BannerImage = ({ title, subTitle }) => {
  return (
    <section>
      <div
        style={{
          backgroundImage: `url(${hmeImg})`,
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          backgroundSize: "cover",
        }}
      >
        <div className="container" style={{ height: "700px" }}>
          <div className="text-center justify-content-center align-self-center px-5">
            <h1 className="pt-5 pb-3" style={{ color: "black" }}>
              {title}
            </h1>
            <Link to='/stories' style={{textDecoration: 'none'}}>
              <Button
                variant="contained"
                style={{ size: 'large', backgroundColor: 'black' }}
                >
                  {subTitle}
                </Button>
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};
