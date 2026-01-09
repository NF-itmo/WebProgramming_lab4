import TopbarSkeleton from "../../UI/topbarSkeleton/TopbarSkeleton";
import DropdownMenu from "../../components/dropdownMenu/DropdownMenu";
import { logoutReq } from "./api/logoutReq";

const Topbar = () => {
  return (
    <TopbarSkeleton>
      <DropdownMenu 
        name="settings"
        options={[
          {
            name: "logout",
            onClick: () => logoutReq({})
          }
        ]}
      />
    </TopbarSkeleton>
  );
};

export default Topbar;
