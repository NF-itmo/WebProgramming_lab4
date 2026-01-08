import "./Main.css";

import Form from "../../modules/plotForm/PlotForm";
import { ColumnLayout as ContentContainer } from "@packages/shared";
import Header from "../../components/header/Header";
import React from "react";

const PlotComponent = React.lazy(() =>
  import('plotComponent/Main').catch(() => ({
    default: () => <div>Error loading Plot component</div>,
  }))
);
const HistoryComponent = React.lazy(() =>
  import('historyComponent/Main').catch(() => ({
    default: () => <div>Error loading History component</div>,
  }))
);

const GroupsComponent = React.lazy(() =>
  import('groupComponent/Main').catch(() => ({
    default: () => <div>Error loading Group component</div>,
  }))
);

const Main = () => {
  return (
    <ContentContainer>
      
      <Header/>

      <React.Suspense fallback={<div>Loading Groups...</div>}>
        <GroupsComponent/>
      </React.Suspense>

      <React.Suspense fallback={<div>Loading Plot...</div>}>
        <PlotComponent/>
      </React.Suspense>

      <Form/>
      
      <React.Suspense fallback={<div>Loading History...</div>}>
        <HistoryComponent/>
      </React.Suspense>

    </ContentContainer>
  );
}

export default Main;