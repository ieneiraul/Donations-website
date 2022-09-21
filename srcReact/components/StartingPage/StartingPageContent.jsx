import classes from '../StartingPage/StartingPageContent.css';
import { BannerImage } from './BannerImage';

const StartingPageContent = () => {
  return (
    <section className={classes.starting}>
      <BannerImage title={"„Fiecare om este o ghindă care poate deveni un stejar dacă această ghindă este plantată pe un teren fertil şi este îngrijită suficient de mult ca să poată prinde rădăcini.”"} subTitle={"Vezi anunturile"}></BannerImage>
      {/* <BannerImage title={"„The young buds must be allowed to bloom”"}  subTitle={"Vezi anunturile"}></BannerImage> */}
    </section>
  );
};

export default StartingPageContent;