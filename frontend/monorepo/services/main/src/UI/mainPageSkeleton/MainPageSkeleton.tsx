import { ReactNode } from "react";

import { ColumnLayout as ContentContainer } from "@packages/shared";

type Props = {
  header: ReactNode;
  body: ReactNode | ReactNode[]
}

const MainPageSkeleton = ({
  header,
  body
}: Props ) => {
  return (
    <>
      
      <ContentContainer>
        { header }
        { body }
      </ContentContainer>
    </>
  )
}

export default MainPageSkeleton;