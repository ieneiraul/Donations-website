import UpdateForm from './UpdateForm';
import classes from './UserProfile.module.css';

const UserProfileEdit = () => {
  return (
    <section className={classes.profile}>
      <UpdateForm />
    </section>
  );
};

export default UserProfileEdit;